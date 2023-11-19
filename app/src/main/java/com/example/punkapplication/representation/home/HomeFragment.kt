package com.example.punkapplication.representation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.punkapplication.databinding.FragmentHomeBinding
import com.example.punkapplication.representation.details.DetailsFragment
import com.example.punkapplication.representation.details.DetailsViewModel
import com.example.punkapplication.representation.ui.DrinkListPagingDataAdapter
import com.example.punkapplication.representation.utils.collectLatestFlow

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels { HomeViewModel.provideFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentHomeBinding.setUpState() {
        setUpBannerState()
        setUpDrinkListState()
    }

    private fun FragmentHomeBinding.setUpBannerState() {
        collectLatestFlow(viewModel.homeBannerUiState) { uiState ->
            val drink = uiState.data

            appBarLayout.setExpanded(uiState.isTopBarExpanded)
            bannerProgressBar.isVisible = uiState.isLoading
            bannerInfoLinearLayout.isVisible = uiState.errorMessage.isBlank()
            bannerErrorTextView.isVisible = uiState.errorMessage.isNotBlank()

            bannerTaglineTextView.text = drink?.tagline
            bannerImageView.load(drink?.imageUrl) { crossfade(true) }
            bannerErrorTextView.text = uiState.errorMessage
        }
    }

    private fun FragmentHomeBinding.setUpDrinkListState() {
        val pagingDataAdapter = DrinkListPagingDataAdapter { drinkId -> showDrinkDetails(drinkId) }
        recyclerView.adapter = pagingDataAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        collectLatestFlow(viewModel.drinkListPagingData) {
            pagingDataAdapter.submitData(it)
        }

        collectLatestFlow(pagingDataAdapter.loadStateFlow) { loadState ->
            drinkListProgressBar.isVisible = loadState.refresh is LoadState.Loading
            recyclerView.isVisible = loadState.refresh !is LoadState.Loading

            if (loadState.refresh is LoadState.Error) {
                val errorMessage = (loadState.refresh as LoadState.Error).error.message
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDrinkDetails(drinkId: Int) {
        activity?.supportFragmentManager?.let { fragmentManager ->
            val bundle = Bundle()
            bundle.putInt(DetailsViewModel.PARAM_DRINK_ID, drinkId)
            DetailsFragment().apply {
                arguments = bundle
                show(fragmentManager, DetailsFragment::class.toString())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveTopBarState(!binding.appBarLayout.isLifted)
    }
}
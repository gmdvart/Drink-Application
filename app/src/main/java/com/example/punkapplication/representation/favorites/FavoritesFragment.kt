package com.example.punkapplication.representation.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.punkapplication.databinding.FragmentFavoritesBinding
import com.example.punkapplication.representation.ui.FavoritesAdapter
import com.example.punkapplication.representation.utils.collectLatestFlow

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel: FavoriteViewModel by viewModels { FavoriteViewModel.provideFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentFavoritesBinding.setUpState() {
        val adapter = FavoritesAdapter { viewModel.removeDrinkFromFavorite(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        collectLatestFlow(viewModel.drinkUiState) { uiState ->
            progressBar.isVisible = uiState is FavoriteDrinkUiState.Loading

            if (uiState is FavoriteDrinkUiState.Loaded) {
                appBarLayout.setExpanded(uiState.isTopBarExpanded)

                drinkAmountCardView.isVisible = uiState.amountOfDrinks != 0
                counterTextView.text = uiState.amountOfDrinks.toString()

                emptyFavoritesTextView.isVisible = uiState.amountOfDrinks == 0

                adapter.submitList(uiState.drinks)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val isTopBarExpanded = !binding.appBarLayout.isLifted
        viewModel.saveTopBarState(isTopBarExpanded)
    }
}
package com.example.punkapplication.representation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.punkapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        binding.setUpBannerState()
    }

    private fun FragmentHomeBinding.setUpBannerState() {
        collectLatestFlow(viewModel.homeBannerUiState) { uiState ->
            val drink = uiState.data

            bannerProgressBar.isVisible = uiState.isLoading
            bannerInfoLinearLayout.isVisible = uiState.errorMessage.isBlank()
            bannerErrorTextView.isVisible = uiState.errorMessage.isNotBlank()

            bannerTaglineTextView.text = drink?.tagline
            bannerImageView.load(drink?.imageUrl) { crossfade(true) }
            bannerErrorTextView.text = uiState.errorMessage
        }
    }

    private fun <T> Fragment.collectLatestFlow(
        flow: Flow<T>,
        block: suspend (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest {
                    block(it)
                }
            }
        }
    }
}
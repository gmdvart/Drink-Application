package com.example.punkapplication.representation.history

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.punkapplication.R
import com.example.punkapplication.databinding.FragmentHistoryBinding
import com.example.punkapplication.representation.ui.HistoryAdapter
import com.example.punkapplication.representation.utils.collectLatestFlow
import com.example.punkapplication.representation.utils.setUpRecyclerViewWithSwipeToRemove

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModels { HistoryViewModel.provideFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentHistoryBinding.setUpState() {
        setUpToolBar()

        val adapter = HistoryAdapter()
        setUpRecyclerViewWithSwipeToRemove(adapter) { viewModel.onEvent(DrinkHistoryEvent.RemoveDrink(it)) }

        collectLatestFlow(viewModel.drinkHistoryUiState) { uiState ->
            progressBar.isVisible = uiState is DrinkHistoryUiState.Loading

            if (uiState is DrinkHistoryUiState.Loaded) {
                emptyHistoryTextView.isVisible = uiState.drinks.isEmpty()
                adapter.submitList(uiState.drinks)
            }
        }
    }

    private fun FragmentHistoryBinding.setUpToolBar() {
        toolBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.clearHistoryItem) {
                viewModel.onEvent(DrinkHistoryEvent.ClearHistory)
                true
            } else
                false
        }
    }
}
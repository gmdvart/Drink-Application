package com.example.punkapplication.representation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.load
import com.example.punkapplication.R
import com.example.punkapplication.databinding.FragmentDetailsBinding
import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.representation.utils.collectLatestFlow
import com.example.punkapplication.representation.utils.getAndInitFavoriteFab
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by viewModels { DetailsViewModel.provideFactory( getDrinkId() ) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentDetailsBinding.setUpState() {
        val favoriteFab = getAndInitFavoriteFab()

        collectLatestFlow(viewModel.drinkDetailsUiState) { uiState ->
            progressBar.isVisible = uiState.isLoading

            uiState.drinkModel?.let {
                imageView.load(it.imageUrl) { crossfade(true) }

                if (!uiState.isLoading ) {
                    setUpDrinkInfo(drinkModel = it)
                    favoriteFab.setUpState(uiState)
                }
            }
        }
    }

    private fun FragmentDetailsBinding.setUpDrinkInfo(drinkModel: DrinkModel) {
        taglineTextView.text = drinkModel.tagline
        nameTextView.text = drinkModel.name
        descriptionTextView.text = drinkModel.description
        firstBrewedTextView.text = drinkModel.firstBrewed
        brewedTipsTextView.text = drinkModel.brewersTips
        var foodPairing = ""
        for (food in drinkModel.foodPairing) {
            foodPairing += "$food\n"
            foodPairingTextView.text = foodPairing

        }
        contributedByTextView.text = drinkModel.contributedBy
    }

    private fun FloatingActionButton.setUpState(uiState: DrinkDetailsUiState) {
        if (uiState.isMarkedAsFavorite) {
            setImageResource(R.drawable.favorite_added)
            setOnClickListener { viewModel.onDetailsEvent(DetailsEvent.Remove) }
        } else {
            setImageResource(R.drawable.favorite_not_added)
            setOnClickListener { viewModel.onDetailsEvent(DetailsEvent.Add) }
        }
        show()
    }

    private fun getDrinkId(): Int? = arguments?.getInt(DetailsViewModel.PARAM_DRINK_ID)
}
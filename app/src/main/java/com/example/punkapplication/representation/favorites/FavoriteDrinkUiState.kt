package com.example.punkapplication.representation.favorites

import com.example.punkapplication.domain.model.DrinkModel

sealed class FavoriteDrinkUiState {
    data object Loading: FavoriteDrinkUiState()
    data class Loaded(
        val drinks: List<DrinkModel> = listOf(),
        val amountOfDrinks: Int = 0
    ) : FavoriteDrinkUiState()
}

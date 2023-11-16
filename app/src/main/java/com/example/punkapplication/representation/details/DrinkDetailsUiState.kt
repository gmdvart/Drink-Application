package com.example.punkapplication.representation.details

import com.example.punkapplication.domain.model.DrinkModel

data class DrinkDetailsUiState(
    val isLoading: Boolean = false,
    val drinkModel: DrinkModel? = null,
    val errorMessage: String = "",
    val isMarkedAsFavorite: Boolean = false
)
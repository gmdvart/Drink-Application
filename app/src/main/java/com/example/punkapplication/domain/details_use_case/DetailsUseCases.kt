package com.example.punkapplication.domain.details_use_case

import com.example.punkapplication.domain.favorites_use_case.RemoveDrinkFromFavoritesUseCase

data class DetailsUseCases(
    val getDrinkDetailsUseCase: GetDrinkDetailsUseCase,
    val addDrinkToFavoritesUseCase: AddDrinkToFavoritesUseCase,
    val getDrinkFromFavoritesUseCase: GetDrinkFromFavoritesUseCase,
    val removeDrinkFromFavoritesUseCase: RemoveDrinkFromFavoritesUseCase,
    val addDrinkToHistoryUseCase: AddDrinkToHistoryUseCase
)

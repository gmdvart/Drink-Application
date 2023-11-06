package com.example.punkapplication.domain.details_use_case

import com.example.punkapplication.domain.repository.DrinkFavoriteRepository

class RemoveDrinkFromHistoryUseCase(
    private val favoriteRepository: DrinkFavoriteRepository
) {
    suspend operator fun invoke(drinkId: Int) {
        favoriteRepository.removeDrink(drinkId)
    }
}
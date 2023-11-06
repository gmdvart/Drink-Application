package com.example.punkapplication.domain.favorites_use_case

import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.domain.repository.DrinkFavoriteRepository

class RemoveDrinkFromFavoritesUseCase(
    private val favoriteRepository: DrinkFavoriteRepository
) {
    suspend operator fun invoke(drink: DrinkModel) {
        favoriteRepository.removeDrink(drink.id)
    }
}
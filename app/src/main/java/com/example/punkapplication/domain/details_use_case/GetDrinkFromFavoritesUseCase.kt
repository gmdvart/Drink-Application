package com.example.punkapplication.domain.details_use_case

import com.example.punkapplication.data.mappers.toDrinkModel
import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.domain.repository.DrinkFavoriteRepository

class GetDrinkFromFavoritesUseCase(
    private val favoriteRepository: DrinkFavoriteRepository
) {
    suspend operator fun invoke(drinkId: Int): DrinkModel? {
        return favoriteRepository.getDrink(drinkId)?.toDrinkModel()
    }
}
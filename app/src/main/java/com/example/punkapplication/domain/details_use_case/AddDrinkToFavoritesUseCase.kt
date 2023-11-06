package com.example.punkapplication.domain.details_use_case

import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.domain.repository.DrinkFavoriteRepository
import com.example.punkapplication.data.mappers.toDrinkFavoriteEntity

class AddDrinkToFavoritesUseCase(
    private val drinkFavoriteRepository: DrinkFavoriteRepository
) {
    suspend operator fun invoke(drink: DrinkModel) {
        drinkFavoriteRepository.insertDrink(drink.toDrinkFavoriteEntity())
    }
}
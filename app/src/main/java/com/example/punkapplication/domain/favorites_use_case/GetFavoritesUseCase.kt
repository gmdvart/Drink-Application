package com.example.punkapplication.domain.favorites_use_case

import com.example.punkapplication.data.local.DrinkFavoriteEntity
import com.example.punkapplication.domain.repository.DrinkFavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val drinkFavoriteRepository: DrinkFavoriteRepository
) {

    operator fun invoke(): Flow<List<DrinkFavoriteEntity>> {
        return drinkFavoriteRepository.getDrinks()
    }
}

package com.example.punkapplication.domain.repository

import com.example.punkapplication.data.local.DrinkFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface DrinkFavoriteRepository {
    fun getDrinks(): Flow<List<DrinkFavoriteEntity>>

    suspend fun getDrink(drinkId: Int): DrinkFavoriteEntity?

    suspend fun removeDrink(drinkId: Int)

    suspend fun insertDrink(drink: DrinkFavoriteEntity)
}
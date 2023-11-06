package com.example.punkapplication.domain.repository

import com.example.punkapplication.data.local.DrinkHistoryEntity
import kotlinx.coroutines.flow.Flow

interface DrinkHistoryRepository {

    fun getDrinks(): Flow<List<DrinkHistoryEntity>>

    suspend fun insertDrink(drink: DrinkHistoryEntity)

    suspend fun clearDrinks()

    suspend fun removeDrink(drinkId: Int)
}
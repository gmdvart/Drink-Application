package com.example.punkapplication.data.repository

import com.example.punkapplication.data.local.DrinkHistoryEntity
import com.example.punkapplication.data.local.dao.DrinkHistoryDao
import com.example.punkapplication.domain.repository.DrinkHistoryRepository
import kotlinx.coroutines.flow.Flow

class DrinkHistoryRepositoryImpl(
    private val drinkHistoryDao: DrinkHistoryDao
) : DrinkHistoryRepository {

    override fun getDrinks(): Flow<List<DrinkHistoryEntity>> = drinkHistoryDao.gerDrinks()

    override suspend fun insertDrink(drink: DrinkHistoryEntity) = drinkHistoryDao.insertDrink(drink)

    override suspend fun clearDrinks() = drinkHistoryDao.clearAll();

    override suspend fun removeDrink(drinkId: Int) = drinkHistoryDao.removeDrink(drinkId)
}
package com.example.punkapplication.data.repository

import com.example.punkapplication.data.local.DrinkFavoriteEntity
import com.example.punkapplication.data.local.dao.DrinkFavoriteDao
import com.example.punkapplication.domain.repository.DrinkFavoriteRepository
import kotlinx.coroutines.flow.Flow

class DrinkFavoriteRepositoryImpl(
    private val drinkFavoriteDao: DrinkFavoriteDao
) : DrinkFavoriteRepository {

    override fun getDrinks(): Flow<List<DrinkFavoriteEntity>> = drinkFavoriteDao.getDrinks()

    override suspend fun getDrink(drinkId: Int): DrinkFavoriteEntity? = drinkFavoriteDao.getDrink(drinkId)

    override suspend fun removeDrink(drinkId: Int) = drinkFavoriteDao.removeDrink(drinkId)

    override suspend fun insertDrink(drink: DrinkFavoriteEntity) = drinkFavoriteDao.insertDrink(drink)
}
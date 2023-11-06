package com.example.punkapplication.data.local.dao

import androidx.room.*
import com.example.punkapplication.data.local.DrinkFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drink: DrinkFavoriteEntity)

    @Query("SELECT * FROM favorites WHERE id = :drinkId")
    suspend fun getDrink(drinkId: Int): DrinkFavoriteEntity?

    @Query("SELECT * FROM favorites")
    fun getDrinks(): Flow<List<DrinkFavoriteEntity>>

    @Query("DELETE FROM favorites WHERE id = :drinkId")
    suspend fun removeDrink(drinkId: Int)
}
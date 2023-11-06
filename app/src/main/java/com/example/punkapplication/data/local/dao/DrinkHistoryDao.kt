package com.example.punkapplication.data.local.dao

import androidx.room.*
import com.example.punkapplication.data.local.DrinkHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drink: DrinkHistoryEntity)

    @Query("SELECT * FROM history ORDER BY time DESC")
    fun gerDrinks(): Flow<List<DrinkHistoryEntity>>

    @Query("DELETE FROM history WHERE id = :drinkId")
    suspend fun removeDrink(drinkId: Int)

    @Query("DELETE FROM history")
    suspend fun clearAll()
}
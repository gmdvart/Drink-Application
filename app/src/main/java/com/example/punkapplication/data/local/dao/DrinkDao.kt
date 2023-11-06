package com.example.punkapplication.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.punkapplication.data.local.DrinkEntity

@Dao
interface DrinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinks(drinks: List<DrinkEntity>)

    @Query("SELECT * FROM cache")
    fun getDrinks(): PagingSource<Int, DrinkEntity>

    @Query("DELETE FROM cache")
    suspend fun clearDrinks()
}
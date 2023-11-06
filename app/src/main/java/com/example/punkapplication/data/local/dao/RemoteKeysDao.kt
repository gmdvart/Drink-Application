package com.example.punkapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.punkapplication.data.local.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE drinkId = :drinkId")
    suspend fun getKey(drinkId: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearKeys()
}
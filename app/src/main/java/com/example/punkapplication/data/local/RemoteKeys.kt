package com.example.punkapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val drinkId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
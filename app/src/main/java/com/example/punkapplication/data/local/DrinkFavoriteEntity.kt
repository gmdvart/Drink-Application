package com.example.punkapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class DrinkFavoriteEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val tagline: String,
    val firstBrewed: String,
    val description: String,
    val imageUrl: String,
    val foodPairing: List<String>,
    val brewersTips: String,
    val contributedBy: String
)

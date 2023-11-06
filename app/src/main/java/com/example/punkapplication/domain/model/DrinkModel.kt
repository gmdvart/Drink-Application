package com.example.punkapplication.domain.model

data class DrinkModel(
    val id: Int,
    val name: String,
    val tagline: String,
    val firstBrewed: String,
    val description: String,
    val imageUrl: String,
    val foodPairing: List<String>,
    val brewersTips: String,
    val contributedBy: String,
)

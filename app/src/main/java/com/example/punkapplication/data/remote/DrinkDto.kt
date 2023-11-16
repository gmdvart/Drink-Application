package com.example.punkapplication.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinkDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("tagline") val tagline: String,
    @SerialName("first_brewed") val firstBrewed: String,
    @SerialName("description") val description: String,
    @SerialName("image_url") val imageUrl: String?,
    @SerialName("food_pairing") val foodPairing: List<String>,
    @SerialName("brewers_tips") val brewersTips: String,
    @SerialName("contributed_by") val contributedBy: String
)
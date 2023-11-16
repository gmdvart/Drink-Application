package com.example.punkapplication.data.mappers

import com.example.punkapplication.data.local.DrinkEntity
import com.example.punkapplication.data.local.DrinkFavoriteEntity
import com.example.punkapplication.data.local.DrinkHistoryEntity
import com.example.punkapplication.data.remote.DrinkDto
import com.example.punkapplication.domain.model.DrinkModel

fun DrinkDto.toEntity(): DrinkEntity {
    return DrinkEntity(
        id,
        name,
        tagline,
        firstBrewed,
        description,
        imageUrl ?: "",
        foodPairing,
        brewersTips,
        contributedBy
    )
}

fun DrinkDto.toDrinkModel(): DrinkModel {
    return DrinkModel(
        id,
        name,
        tagline,
        firstBrewed,
        description,
        imageUrl ?: "",
        foodPairing,
        brewersTips,
        contributedBy
    )
}

fun DrinkEntity.toDrinkModel(): DrinkModel {
    return DrinkModel(
        id,
        name,
        tagline,
        firstBrewed,
        description,
        imageUrl,
        foodPairing,
        brewersTips,
        contributedBy
    )
}

fun DrinkFavoriteEntity.toDrinkModel(): DrinkModel {
    return DrinkModel(
        id,
        name,
        tagline,
        firstBrewed,
        description,
        imageUrl,
        foodPairing,
        brewersTips,
        contributedBy,
    )
}

fun DrinkHistoryEntity.toDrinkModel(): DrinkModel {
    return DrinkModel(
        id,
        name,
        tagline,
        firstBrewed,
        description,
        imageUrl,
        foodPairing,
        brewersTips,
        contributedBy,
    )
}

fun DrinkModel.toHistoryDrinkEntity(time: Long): DrinkHistoryEntity {
    return DrinkHistoryEntity(
        id,
        name,
        tagline,
        firstBrewed,
        description,
        imageUrl,
        foodPairing,
        brewersTips,
        contributedBy,
        time
    )
}

fun DrinkModel.toDrinkFavoriteEntity(): DrinkFavoriteEntity {
    return DrinkFavoriteEntity(
        id,
        name,
        tagline,
        firstBrewed,
        description,
        imageUrl,
        foodPairing,
        brewersTips,
        contributedBy
    )
}
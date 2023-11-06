package com.example.punkapplication.domain.repository

import com.example.punkapplication.data.remote.DrinkDto
import com.example.punkapplication.utils.Resource

interface SingleDrinkRepository {
    suspend fun getRandomDrink(): Resource<DrinkDto?>

    suspend fun getSingleDrink(drinkId: Int): Resource<DrinkDto?>
}
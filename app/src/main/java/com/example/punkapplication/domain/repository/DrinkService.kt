package com.example.punkapplication.domain.repository

import com.example.punkapplication.data.remote.DrinkDto

interface DrinkService {

    suspend fun getDrinks(pageIndex: Int, pageSize: Int): List<DrinkDto>

    suspend fun getRandomDrink(): DrinkDto?

    suspend fun getSingleDrink(drinkId: Int): DrinkDto?
}
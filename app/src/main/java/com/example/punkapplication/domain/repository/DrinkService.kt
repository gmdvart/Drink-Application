package com.example.punkapplication.domain.repository

import com.example.punkapplication.data.remote.DrinkDto

interface DrinkService {

    suspend fun getDrinks(pageIndex: Int, pageSize: Int): List<DrinkDto>

    suspend fun getRandomDrink(): List<DrinkDto>

    suspend fun getSingleDrink(drinkId: Int): List<DrinkDto>

    companion object {
        const val PAGE_SIZE = 20
        const val PAGE_START_INDEX = 1
    }
}
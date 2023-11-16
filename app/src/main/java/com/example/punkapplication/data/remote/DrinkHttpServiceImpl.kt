package com.example.punkapplication.data.remote

import com.example.punkapplication.domain.repository.DrinkService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class DrinkHttpServiceImpl(
    private val client: HttpClient
) : DrinkService {

    override suspend fun getDrinks(pageIndex: Int, pageSize: Int): List<DrinkDto> {
        return client.get(DrinkHttpRoutes.BEERS) {
            parameter(DrinkHttpParameters.PAGE_INDEX, pageIndex)
            parameter(DrinkHttpParameters.PAGE_SIZE, pageSize)
        }.body()
    }

    override suspend fun getRandomDrink(): List<DrinkDto> {
        return client.get(DrinkHttpRoutes.RANDOM).body()
    }

    override suspend fun getSingleDrink(drinkId: Int): List<DrinkDto> {
        return client.get(DrinkHttpRoutes.BEERS + "/$drinkId").body()
    }
}
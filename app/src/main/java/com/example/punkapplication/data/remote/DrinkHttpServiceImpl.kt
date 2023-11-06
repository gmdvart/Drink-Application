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
        return client.get {
            url(DrinkHttpRoutes.BEERS)
            parameters {
                append(DrinkHttpParameters.PAGE_INDEX, pageIndex.toString())
                append(DrinkHttpParameters.PAGE_SIZE, pageSize.toString())
            }
        }.body()
    }

    override suspend fun getRandomDrink(): DrinkDto? {
        return client.get { url(DrinkHttpRoutes.RANDOM) }.body()
    }

    override suspend fun getSingleDrink(drinkId: Int): DrinkDto? {
        return client.get { url(DrinkHttpRoutes.BEERS + "/$drinkId") }.body()
    }
}
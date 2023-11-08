package com.example.punkapplication.data.remote

import android.util.Log
import com.example.punkapplication.domain.repository.DrinkService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class DrinkHttpServiceImpl(
    private val client: HttpClient
) : DrinkService {

    override suspend fun getDrinks(pageIndex: Int, pageSize: Int): List<DrinkDto> {
        return client.get(DrinkHttpRoutes.BEERS) {
            parameters {
                append(DrinkHttpParameters.PAGE_INDEX, pageIndex.toString())
                append(DrinkHttpParameters.PAGE_SIZE, pageSize.toString())
            }
        }.body()
    }

    override suspend fun getRandomDrink(): List<DrinkDto> {
        return client.get(DrinkHttpRoutes.RANDOM).body()
    }

    override suspend fun getSingleDrink(drinkId: Int): List<DrinkDto> {
        return client.get(DrinkHttpRoutes.BEERS + "/$drinkId").body()
    }
}
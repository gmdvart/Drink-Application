package com.example.punkapplication.data.remote

object DrinkHttpRoutes {
    private const val BASE_URL = "https://api.punkapi.com/v2/"
    const val RANDOM = "$BASE_URL/beers/random"
    const val BEERS = "$BASE_URL/beers"
}
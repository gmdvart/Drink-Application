package com.example.punkapplication.di

import android.content.Context
import com.example.punkapplication.data.local.DrinkDatabase
import com.example.punkapplication.data.remote.DrinkHttpServiceImpl
import com.example.punkapplication.data.repository.DrinkFavoriteRepositoryImpl
import com.example.punkapplication.data.repository.DrinkHistoryRepositoryImpl
import com.example.punkapplication.data.repository.DrinkListRepositoryImpl
import com.example.punkapplication.data.repository.SingleDrinkRepositoryImpl
import com.example.punkapplication.domain.details_use_case.*
import com.example.punkapplication.domain.favorites_use_case.FavoritesUseCases
import com.example.punkapplication.domain.favorites_use_case.GetFavoritesUseCase
import com.example.punkapplication.domain.favorites_use_case.RemoveDrinkFromFavoritesUseCase
import com.example.punkapplication.domain.history_use_case.ClearDrinkHistoryUseCase
import com.example.punkapplication.domain.history_use_case.GetDrinkHistoryListUseCase
import com.example.punkapplication.domain.history_use_case.HistoryUseCases
import com.example.punkapplication.domain.history_use_case.RemoveDrinkFromHistoryUseCase
import com.example.punkapplication.domain.home_use_case.GetDrinkListUseCase
import com.example.punkapplication.domain.home_use_case.GetRandomDrinkUseCase
import com.example.punkapplication.domain.home_use_case.HomeUseCases
import com.example.punkapplication.domain.repository.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class PunkAppModule(
    private val context: Context
) : AppModule {

    private val database by lazy { provideDatabase() }
    private val service by lazy { provideService() }
    private val drinkFavoriteRepository by lazy { provideDrinkFavoriteRepository() }
    private val drinkHistoryRepository by lazy { provideDrinkHistoryRepository() }
    private val drinkListRepository by lazy { provideDrinkListRepository() }
    private val singleDrinkRepository by lazy { provideSingleDrinkRepository() }

    private fun provideDatabase(): DrinkDatabase {
        return DrinkDatabase.getDatabase(context)
    }

    private fun provideService(): DrinkService {
        return DrinkHttpServiceImpl(
            client = HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    val jsonConfig = Json { ignoreUnknownKeys = true }
                    json(jsonConfig)
                }
            }
        )
    }

    private fun provideDrinkFavoriteRepository(): DrinkFavoriteRepository {
        return DrinkFavoriteRepositoryImpl(database.drinkFavoriteDao)
    }

    private fun provideDrinkHistoryRepository(): DrinkHistoryRepository {
        return DrinkHistoryRepositoryImpl(database.drinkHistoryDao)
    }

    private fun provideDrinkListRepository(): DrinkListRepository {
        return DrinkListRepositoryImpl(database = database, service =  service)
    }

    private fun provideSingleDrinkRepository(): SingleDrinkRepository {
        return SingleDrinkRepositoryImpl(service)
    }

    override fun provideFavoritesUseCases(): FavoritesUseCases {
        return FavoritesUseCases(
            GetFavoritesUseCase(drinkFavoriteRepository),
            RemoveDrinkFromFavoritesUseCase(drinkFavoriteRepository)
        )
    }

    override fun provideHistoryUseCases(): HistoryUseCases {
        return HistoryUseCases(
            ClearDrinkHistoryUseCase(drinkHistoryRepository),
            GetDrinkHistoryListUseCase(drinkHistoryRepository),
            RemoveDrinkFromHistoryUseCase(drinkHistoryRepository)
        )
    }

    override fun provideHomeUseCases(): HomeUseCases {
        return HomeUseCases(
            GetRandomDrinkUseCase(singleDrinkRepository),
            GetDrinkListUseCase(drinkListRepository)
        )
    }

    override fun provideDetailsUseCases(): DetailsUseCases {
        return DetailsUseCases(
            GetDrinkDetailsUseCase(singleDrinkRepository),
            AddDrinkToFavoritesUseCase(drinkFavoriteRepository),
            GetDrinkFromFavoritesUseCase(drinkFavoriteRepository),
            RemoveDrinkFromFavoritesUseCase(drinkFavoriteRepository),
            AddDrinkToHistoryUseCase(drinkHistoryRepository)
        )
    }
}
package com.example.punkapplication.di

import com.example.punkapplication.domain.details_use_case.DetailsUseCases
import com.example.punkapplication.domain.favorites_use_case.FavoritesUseCases
import com.example.punkapplication.domain.history_use_case.HistoryUseCases
import com.example.punkapplication.domain.home_use_case.HomeUseCases

interface AppModule {
    fun provideFavoritesUseCases(): FavoritesUseCases

    fun provideHistoryUseCases(): HistoryUseCases

    fun provideHomeUseCases(): HomeUseCases

    fun provideDetailsUseCases(): DetailsUseCases
}
package com.example.punkapplication.domain.repository

import androidx.paging.PagingData
import com.example.punkapplication.data.local.DrinkEntity
import kotlinx.coroutines.flow.Flow

interface DrinkListRepository {
    fun getDrinkListStream(): Flow<PagingData<DrinkEntity>>
}
package com.example.punkapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.punkapplication.data.local.DrinkDatabase
import com.example.punkapplication.data.local.DrinkEntity
import com.example.punkapplication.data.pagination.DrinkRemoteMediator
import com.example.punkapplication.domain.repository.DrinkListRepository
import com.example.punkapplication.domain.repository.DrinkService
import kotlinx.coroutines.flow.Flow


class DrinkListRepositoryImpl(
    private val service: DrinkService,
    private val database: DrinkDatabase
) : DrinkListRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getDrinkListStream(): Flow<PagingData<DrinkEntity>> {
        val pagingSourceFactory = { database.drinkDao.getDrinks() }
        return Pager(
            config = PagingConfig(
                pageSize = DrinkService.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = DrinkRemoteMediator(service, database)
        ).flow
    }
}
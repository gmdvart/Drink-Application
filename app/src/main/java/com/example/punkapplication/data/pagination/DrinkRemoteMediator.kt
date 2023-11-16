package com.example.punkapplication.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.punkapplication.data.local.DrinkDatabase
import com.example.punkapplication.data.local.DrinkEntity
import com.example.punkapplication.data.local.RemoteKeys
import com.example.punkapplication.data.mappers.toEntity
import com.example.punkapplication.domain.repository.DrinkService
import io.ktor.client.plugins.*

@OptIn(ExperimentalPagingApi::class)
class DrinkRemoteMediator(
    private val service: DrinkService,
    private val database: DrinkDatabase
) : RemoteMediator<Int, DrinkEntity>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, DrinkEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeysForCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: DrinkService.PAGE_START_INDEX
                DrinkService.PAGE_START_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey =
                    remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey =
                    remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val drinks = service.getDrinks(pageIndex = page, pageSize = state.config.pageSize)
            val endOfPaginationReached = drinks.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.drinkDao.clearDrinks()
                    database.remoteKeysDao.clearKeys()
                }

                val prevKey = if (page == DrinkService.PAGE_START_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = drinks.map { drink -> RemoteKeys(drink.id, prevKey, nextKey) }
                val drinksEntities = drinks.map { it.toEntity() }

                database.remoteKeysDao.insertKeys(keys)
                database.drinkDao.insertDrinks(drinksEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: RedirectResponseException) {
            return MediatorResult.Error(e)
        } catch (e: ClientRequestException) {
            return MediatorResult.Error(e)
        } catch (e: ServerResponseException) {
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, DrinkEntity>): RemoteKeys? {
        return state.pages.lastOrNull { page -> page.data.isNotEmpty() }?.data?.lastOrNull()?.let { drink ->
            database.remoteKeysDao.getKey(drink.id)
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, DrinkEntity>): RemoteKeys? {
        return state.pages.firstOrNull { page -> page.data.isNotEmpty() }?.data?.firstOrNull()?.let { drink ->
            database.remoteKeysDao.getKey(drink.id)
        }
    }

    private suspend fun getRemoteKeysForCurrentPosition(state: PagingState<Int, DrinkEntity>): RemoteKeys? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.let { drink ->
                database.remoteKeysDao.getKey(drink.id)
            }
        }
    }
}
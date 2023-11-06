package com.example.punkapplication.domain.home_use_case

import androidx.paging.PagingData
import com.example.punkapplication.data.local.DrinkEntity
import com.example.punkapplication.domain.repository.DrinkListRepository
import kotlinx.coroutines.flow.Flow

class GetDrinkListUseCase(
    private val drinkListRepository: DrinkListRepository
) {
    operator fun invoke(): Flow<PagingData<DrinkEntity>> = drinkListRepository.getDrinkListStream()
}
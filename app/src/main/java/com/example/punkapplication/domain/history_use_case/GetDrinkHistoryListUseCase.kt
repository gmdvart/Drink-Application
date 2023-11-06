package com.example.punkapplication.domain.history_use_case

import com.example.punkapplication.data.local.DrinkHistoryEntity
import com.example.punkapplication.domain.repository.DrinkHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetDrinkHistoryListUseCase(
    private val drinkHistoryRepository: DrinkHistoryRepository
) {
    operator fun invoke(): Flow<List<DrinkHistoryEntity>> {
        return drinkHistoryRepository.getDrinks()
    }
}
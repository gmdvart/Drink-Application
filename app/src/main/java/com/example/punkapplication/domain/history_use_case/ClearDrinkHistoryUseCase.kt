package com.example.punkapplication.domain.history_use_case

import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.domain.repository.DrinkHistoryRepository

class ClearDrinkHistoryUseCase(
    private val drinkHistoryRepository: DrinkHistoryRepository
) {
    suspend operator fun invoke() {
        drinkHistoryRepository.clearDrinks()
    }
}
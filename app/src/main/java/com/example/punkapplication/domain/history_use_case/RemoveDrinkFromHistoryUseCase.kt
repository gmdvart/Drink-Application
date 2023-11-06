package com.example.punkapplication.domain.history_use_case

import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.domain.repository.DrinkHistoryRepository

class RemoveDrinkFromHistoryUseCase(
    private val drinkHistoryRepository: DrinkHistoryRepository
) {
    suspend operator fun invoke(drink: DrinkModel) {
        drinkHistoryRepository.removeDrink(drink.id)
    }
}
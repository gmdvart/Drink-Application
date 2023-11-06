package com.example.punkapplication.domain.details_use_case

import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.domain.repository.DrinkHistoryRepository
import com.example.punkapplication.data.mappers.toHistoryDrinkEntity

class AddDrinkToHistoryUseCase(
    private val drinkHistoryRepository: DrinkHistoryRepository
) {
    suspend operator fun invoke(drink: DrinkModel, time: Long) {
        drinkHistoryRepository.insertDrink(drink.toHistoryDrinkEntity(time))
    }
}
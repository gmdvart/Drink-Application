package com.example.punkapplication.representation.history

import com.example.punkapplication.domain.model.DrinkModel

sealed class DrinkHistoryEvent {
    data class RemoveDrink(val drink: DrinkModel): DrinkHistoryEvent()
    data object ClearHistory: DrinkHistoryEvent()
}
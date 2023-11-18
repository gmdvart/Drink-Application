package com.example.punkapplication.representation.history

import com.example.punkapplication.domain.model.DrinkModel

sealed class HistoryEvent {
    data class Remove(val drink: DrinkModel): HistoryEvent()
    data object ClearHistory: HistoryEvent()
}
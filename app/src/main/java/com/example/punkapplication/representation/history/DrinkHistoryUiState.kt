package com.example.punkapplication.representation.history

import com.example.punkapplication.domain.model.DrinkModel

sealed class DrinkHistoryUiState {
    data object Loading: DrinkHistoryUiState()
    data class Loaded(val drinks: List<DrinkModel>): DrinkHistoryUiState()
}

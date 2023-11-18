package com.example.punkapplication.representation.history

import com.example.punkapplication.domain.model.DrinkModel

sealed class HistoryUiState {
    data object Loading: HistoryUiState()
    data class Loaded(val drinks: List<DrinkModel>): HistoryUiState()
}

package com.example.punkapplication.domain.history_use_case

data class HistoryUseCases(
    val clearDrinkHistoryUseCase: ClearDrinkHistoryUseCase,
    val getDrinkHistoryListUseCase: GetDrinkHistoryListUseCase,
    val removeDrinkFromHistoryUseCase: RemoveDrinkFromHistoryUseCase
)

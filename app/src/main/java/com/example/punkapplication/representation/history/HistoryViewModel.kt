package com.example.punkapplication.representation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.punkapplication.domain.history_use_case.HistoryUseCases
import com.example.punkapplication.data.mappers.toDrinkModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HistoryViewModel (
    private val historyUseCases: HistoryUseCases
) : ViewModel() {

    private val _drinkHistoryUiState: MutableStateFlow<DrinkHistoryUiState> = MutableStateFlow(DrinkHistoryUiState.Loading)
    val drinkHistoryUiState: StateFlow<DrinkHistoryUiState>
        get() = _drinkHistoryUiState.asStateFlow()

    init {
        getDrinks()
    }


    fun onEvent(event: DrinkHistoryEvent) {
        when (event) {
            is DrinkHistoryEvent.RemoveDrink -> {
                viewModelScope.launch {
                    historyUseCases.removeDrinkFromHistoryUseCase(event.drink)
                }
            }
            is DrinkHistoryEvent.ClearHistory -> {
                viewModelScope.launch {
                    historyUseCases.clearDrinkHistoryUseCase()
                }
            }
        }
    }

    private fun getDrinks() {
        viewModelScope.launch {
            _drinkHistoryUiState.value = DrinkHistoryUiState.Loading
            historyUseCases.getDrinkHistoryListUseCase().collectLatest { drinkList ->
                val drinks = drinkList.map { it.toDrinkModel() }
                _drinkHistoryUiState.value = DrinkHistoryUiState.Loaded(drinks)
            }
        }
    }
}
package com.example.punkapplication.representation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.punkapplication.PunkApplication
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

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                        val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PunkApplication
                        val useCases = application.module.provideHistoryUseCases()
                        return HistoryViewModel(useCases) as T
                    } else {
                        throw  IllegalArgumentException("Unknown ViewModel class")
                    }
                }
            }
        }
    }
}
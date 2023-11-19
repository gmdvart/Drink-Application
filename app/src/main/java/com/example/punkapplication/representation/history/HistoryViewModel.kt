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

    private val _historyUiState: MutableStateFlow<HistoryUiState> = MutableStateFlow(HistoryUiState.Loading)
    val historyUiState: StateFlow<HistoryUiState>
        get() = _historyUiState.asStateFlow()

    init {
        getDrinks()
    }


    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.Remove -> {
                viewModelScope.launch {
                    historyUseCases.removeDrinkFromHistoryUseCase(event.drink)
                }
            }
            is HistoryEvent.ClearHistory -> {
                viewModelScope.launch {
                    historyUseCases.clearDrinkHistoryUseCase()
                }
            }
        }
    }

    private fun getDrinks() {
        viewModelScope.launch {
            _historyUiState.value = HistoryUiState.Loading
            historyUseCases.getDrinkHistoryListUseCase().collectLatest { drinkList ->
                val drinks = drinkList.map { it.toDrinkModel() }
                _historyUiState.value = HistoryUiState.Loaded(drinks)
            }
        }
    }

    fun saveTopAppBarState(isTopAppBarExpanded: Boolean) {
        val stateLoaded = _historyUiState.value as? HistoryUiState.Loaded
        stateLoaded?.let { loaded ->
            _historyUiState.update { loaded.copy(isTopBarExpanded = isTopAppBarExpanded) }
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
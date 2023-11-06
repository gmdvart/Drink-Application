package com.example.punkapplication.representation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.punkapplication.utils.Resource
import com.example.punkapplication.data.mappers.toDrinkModel
import com.example.punkapplication.domain.details_use_case.DetailsUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val PARAM_DRINK_ID = "drinkId"

class DetailsViewModel(
    private val detailsUseCases: DetailsUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _drinkDetailsUiState: MutableStateFlow<DrinkDetailsUiState> = MutableStateFlow(DrinkDetailsUiState())
    val drinkDetailsUiState: StateFlow<DrinkDetailsUiState>
        get() = _drinkDetailsUiState.asStateFlow()

    init {
        savedStateHandle.get<Int>(PARAM_DRINK_ID)?.let { getDrinkDetails(it) }
    }

    private fun getDrinkDetails(drinkId: Int) {
        detailsUseCases.getDrinkDetailsUseCase(drinkId).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _drinkDetailsUiState.value = DrinkDetailsUiState(isLoading = true)
                }
                is Resource.Success -> {
                    val drink = resource.data
                    val isMarkedAsFavorite = detailsUseCases.getDrinkFromFavoritesUseCase(drinkId) == null
                    _drinkDetailsUiState.value = DrinkDetailsUiState(
                        drinkModel = drink?.toDrinkModel(),
                        isMarkedAsFavorite = isMarkedAsFavorite
                    )
                }
                is Resource.Error -> {
                    _drinkDetailsUiState.value = DrinkDetailsUiState(errorMessage = resource.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addDrinkToHistory() {
        val drink = _drinkDetailsUiState.value.drinkModel
        viewModelScope.launch {
            val time = System.currentTimeMillis()
            drink?.let { detailsUseCases.addDrinkToHistoryUseCase(drink, time) }
        }
    }

    fun onDetailsEvent(event: DetailsEvent) {
        val drink = _drinkDetailsUiState.value.drinkModel
        when (event) {
            is DetailsEvent.Add -> {
                viewModelScope.launch {
                    drink?.let { detailsUseCases.addDrinkToFavoritesUseCase(it) }
                }
            }
            is DetailsEvent.Remove -> {
                viewModelScope.launch{
                    drink?.let { detailsUseCases.removeDrinkFromFavoritesUseCase(it) }
                }
            }
        }
    }
}

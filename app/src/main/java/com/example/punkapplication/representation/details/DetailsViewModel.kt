package com.example.punkapplication.representation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.punkapplication.PunkApplication
import com.example.punkapplication.data.mappers.toDrinkModel
import com.example.punkapplication.domain.details_use_case.DetailsUseCases
import com.example.punkapplication.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsUseCases: DetailsUseCases,
    drinkId: Int? = null
) : ViewModel() {

    private val _drinkDetailsUiState: MutableStateFlow<DrinkDetailsUiState> =
        MutableStateFlow(DrinkDetailsUiState(isLoading = true))
    val drinkDetailsUiState: StateFlow<DrinkDetailsUiState>
        get() = _drinkDetailsUiState.asStateFlow()

    private var addOrRemoveFromFavoriteJob: Job? = null

    init {
        drinkId?.let { getDrinkDetails(it) }
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
            if (resource !is Resource.Error) checkIsDrinkInFavorites(drinkId)
        }.launchIn(viewModelScope)
    }

    private suspend fun checkIsDrinkInFavorites(drinkId: Int) {
        val isMarkedAsFavorite = detailsUseCases.getDrinkFromFavoritesUseCase(drinkId) != null
        _drinkDetailsUiState.value = _drinkDetailsUiState.value.copy(isMarkedAsFavorite = isMarkedAsFavorite)
    }

    fun onDetailsEvent(event: DetailsEvent) {
        val isRemoveOrAddInProcess = addOrRemoveFromFavoriteJob?.isActive ?: false
        if (isRemoveOrAddInProcess) return

        val drink = _drinkDetailsUiState.value.drinkModel

        addOrRemoveFromFavoriteJob = viewModelScope.launch {
            when (event) {
                is DetailsEvent.Add -> {
                    drink?.let { detailsUseCases.addDrinkToFavoritesUseCase(it) }
                }
                is DetailsEvent.Remove -> {
                    drink?.let { detailsUseCases.removeDrinkFromFavoritesUseCase(it) }
                }
            }
            drink?.id?.let { checkIsDrinkInFavorites(it) }
        }
    }

    private fun addDrinkToHistory() {
        val drink = _drinkDetailsUiState.value.drinkModel
        viewModelScope.launch {
            val time = System.currentTimeMillis()
            drink?.let { detailsUseCases.addDrinkToHistoryUseCase(drink, time) }
        }
    }

    companion object {
        const val PARAM_DRINK_ID = "drinkId"

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(drinkId: Int?): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                )
                        : T {
                    if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                        val application =
                            extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PunkApplication
                        val useCases = application.module.provideDetailsUseCases()
                        return DetailsViewModel(useCases, drinkId) as T
                    } else {
                        throw IllegalArgumentException("Unknown ViewModel class")
                    }
                }
            }
        }
    }
}

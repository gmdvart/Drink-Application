package com.example.punkapplication.representation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.punkapplication.PunkApplication
import com.example.punkapplication.domain.favorites_use_case.FavoritesUseCases
import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.data.mappers.toDrinkModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteViewModel (
    private val favoritesUseCases: FavoritesUseCases
) : ViewModel() {

    private val _drinkUiState: MutableStateFlow<FavoriteDrinkUiState> = MutableStateFlow(FavoriteDrinkUiState.Loading)
    val drinkUiState: StateFlow<FavoriteDrinkUiState>
        get() = _drinkUiState.asStateFlow()

    init {
        getFavoriteDrinks()
    }

    private fun getFavoriteDrinks() {
        viewModelScope.launch {
            _drinkUiState.value = FavoriteDrinkUiState.Loading
            favoritesUseCases.getFavoritesUseCase().collectLatest { drinkList ->
                val drinks = drinkList.map { it.toDrinkModel() }
                _drinkUiState.value = FavoriteDrinkUiState.Loaded(drinks, drinks.size)
            }
        }
    }

    fun removeDrinkFromFavorite(drink: DrinkModel) {
        viewModelScope.launch {
            favoritesUseCases.removeDrinkFromFavoritesUseCase(drink)
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
                    if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                        val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PunkApplication
                        val useCases = application.module.provideFavoritesUseCases()
                        return FavoriteViewModel(useCases) as T
                    } else {
                        throw IllegalArgumentException("Unknown ViewModel class")
                    }
                }
            }
        }
    }
}
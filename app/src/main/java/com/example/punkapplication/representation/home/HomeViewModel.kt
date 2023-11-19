package com.example.punkapplication.representation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.punkapplication.PunkApplication
import com.example.punkapplication.domain.home_use_case.HomeUseCases
import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.utils.Resource
import com.example.punkapplication.data.mappers.toDrinkModel
import kotlinx.coroutines.flow.*

class HomeViewModel (
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _homeBannerUiState: MutableStateFlow<HomeBannerUiState> = MutableStateFlow(HomeBannerUiState(isLoading = true))
    val homeBannerUiState: StateFlow<HomeBannerUiState>
        get() = _homeBannerUiState.asStateFlow()

    val drinkListPagingData: Flow<PagingData<DrinkModel>> =
        homeUseCases.getDrinkListUseCase().map { pagingData ->
            pagingData.map { it.toDrinkModel() }
        }.cachedIn(viewModelScope)

    init {
        getRandomDrink()
    }

    private fun getRandomDrink() {
        homeUseCases.getRandomDrinkUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _homeBannerUiState.value = HomeBannerUiState(isLoading = true)
                }
                is Resource.Success -> {
                    _homeBannerUiState.value = HomeBannerUiState(data = result.data?.toDrinkModel())
                }
                is Resource.Error -> {
                    _homeBannerUiState.value = HomeBannerUiState(errorMessage = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveTopBarState(isTopBarExpanded: Boolean) {
        _homeBannerUiState.update {
            _homeBannerUiState.value.copy(isTopBarExpanded = isTopBarExpanded)
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
                    if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                        val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PunkApplication
                        val useCases = application.module.provideHomeUseCases()
                        return HomeViewModel(useCases) as T
                    } else {
                        throw IllegalArgumentException("Unknown ViewModel class")
                    }
                }
            }
        }
    }
}
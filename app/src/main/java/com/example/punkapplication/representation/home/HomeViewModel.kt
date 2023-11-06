package com.example.punkapplication.representation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.punkapplication.domain.home_use_case.HomeUseCases
import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.utils.Resource
import com.example.punkapplication.data.mappers.toDrinkModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel (
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _homeBannerUiState: MutableStateFlow<HomeBannerUiState> = MutableStateFlow(HomeBannerUiState())
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
}
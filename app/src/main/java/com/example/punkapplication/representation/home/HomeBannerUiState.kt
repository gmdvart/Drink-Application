package com.example.punkapplication.representation.home

import com.example.punkapplication.domain.model.DrinkModel

data class HomeBannerUiState(
    val isLoading: Boolean = false,
    val data: DrinkModel? = null,
    val errorMessage: String = ""
)

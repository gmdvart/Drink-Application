package com.example.punkapplication.domain.details_use_case

import com.example.punkapplication.data.remote.DrinkDto
import com.example.punkapplication.domain.repository.SingleDrinkRepository
import com.example.punkapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDrinkDetailsUseCase(
    private val singleDrinkRepository: SingleDrinkRepository
) {
    operator fun invoke(drinkId: Int): Flow<Resource<DrinkDto?>> = flow {
        emit(Resource.Loading())
        val resource = singleDrinkRepository.getSingleDrink(drinkId)
        emit(resource)
    }
}
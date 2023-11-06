package com.example.punkapplication.representation.details

import com.example.punkapplication.domain.model.DrinkModel

sealed class DetailsEvent {
    data class Remove(val drinkModel: DrinkModel): DetailsEvent()
    data class Add(val drinkModel: DrinkModel): DetailsEvent()
}
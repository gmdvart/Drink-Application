package com.example.punkapplication.representation.details

sealed class DetailsEvent {
    data object Remove: DetailsEvent()
    data object Add: DetailsEvent()
}
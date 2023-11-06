package com.example.punkapplication.data.repository

import com.example.punkapplication.data.remote.DrinkDto
import com.example.punkapplication.domain.repository.DrinkService
import com.example.punkapplication.domain.repository.SingleDrinkRepository
import com.example.punkapplication.utils.Resource
import io.ktor.client.plugins.*

class SingleDrinkRepositoryImpl(
    private val service: DrinkService
) : SingleDrinkRepository {

    override suspend fun getRandomDrink(): Resource<DrinkDto?> {
        return try {
            val randomDrink = service.getRandomDrink()
            Resource.Success(data = randomDrink)
        } catch (e: RedirectResponseException) {
            Resource.Error(message = e.response.status.description)
        } catch (e: ClientRequestException) {
            Resource.Error(message = e.response.status.description)
        } catch (e: ServerResponseException) {
            Resource.Error(message = e.response.status.description)
        } catch (e: Exception) {
            Resource.Error(message = e.message)
        }
    }

    override suspend fun getSingleDrink(drinkId: Int): Resource<DrinkDto?> {
        return try {
            val randomDrink = service.getSingleDrink(drinkId)
            Resource.Success(data = randomDrink)
        } catch (e: RedirectResponseException) {
            Resource.Error(message = e.response.status.description)
        } catch (e: ClientRequestException) {
            Resource.Error(message = e.response.status.description)
        } catch (e: ServerResponseException) {
            Resource.Error(message = e.response.status.description)
        } catch (e: Exception) {
            Resource.Error(message = e.message)
        }
    }
}
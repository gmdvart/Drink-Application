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
            val randomDrink = service.getRandomDrink().first()
            Resource.Success(data = randomDrink)
        } catch (e: RedirectResponseException) {
            return Resource.Error(e.response.status.description)
        } catch (e: ClientRequestException) {
            return Resource.Error(e.response.status.description)
        } catch (e: ServerResponseException) {
            return Resource.Error(e.response.status.description)
        } catch (e: Exception) {
            return Resource.Error(e.message)
        }
    }

    override suspend fun getSingleDrink(drinkId: Int): Resource<DrinkDto?> {
        return try {
            val randomDrink = service.getSingleDrink(drinkId).first()
            Resource.Success(data = randomDrink)
        } catch (e: RedirectResponseException) {
            return Resource.Error(e.response.status.description)
        } catch (e: ClientRequestException) {
            return Resource.Error(e.response.status.description)
        } catch (e: ServerResponseException) {
            return Resource.Error(e.response.status.description)
        } catch (e: Exception) {
            return Resource.Error(e.message)
        }
    }
}
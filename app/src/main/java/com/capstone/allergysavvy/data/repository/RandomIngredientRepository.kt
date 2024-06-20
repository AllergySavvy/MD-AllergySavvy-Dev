package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.response.DataItemIngredientRandom

import com.capstone.allergysavvy.data.retrofit.ApiService

class RandomIngredientRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getRandomIngredient(): Result<List<DataItemIngredientRandom?>> {
        try {
            val response = apiService.getRandomIngredient()
            if (response.mstatus == "OK") {
                val randomIngredient = response.data ?: emptyList()
                return Result.Success(randomIngredient)
            } else {
                return Result.Error("Error : ${response.message}")
            }
        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RandomIngredientRepository? = null

        fun getInstance(
            apiService: ApiService
        ): RandomIngredientRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RandomIngredientRepository(apiService)
            }.also { INSTANCE = it }
    }
}
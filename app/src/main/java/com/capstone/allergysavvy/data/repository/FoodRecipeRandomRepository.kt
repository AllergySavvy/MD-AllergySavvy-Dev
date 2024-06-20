package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.response.DataItemFoodRandom
import com.capstone.allergysavvy.data.retrofit.ApiService

class FoodRecipeRandomRepository(
    private val apiService: ApiService
) {

    suspend fun getRandomFoodRecipes(): Result<List<DataItemFoodRandom?>> {
        try {
            val response = apiService.getFoodRandom()
            if (response.mstatus == "OK") {
                val randomRecipes = response.data ?: emptyList()
                return Result.Success(randomRecipes)
            } else {
                return Result.Error("Error : ${response.message}")
            }
        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FoodRecipeRandomRepository? = null

        fun getInstance(
            apiService: ApiService
        ): FoodRecipeRandomRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FoodRecipeRandomRepository(apiService)
            }.also { INSTANCE = it }
    }
}
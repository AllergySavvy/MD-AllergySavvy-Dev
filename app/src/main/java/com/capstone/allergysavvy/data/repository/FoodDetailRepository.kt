package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.response.DataItemFoodDetail
import com.capstone.allergysavvy.data.retrofit.ApiService

class FoodDetailRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getDetailFoodRecipe(index: Int): Result<DataItemFoodDetail?> {
        try {
            val response = apiService.getFoodRecipesDetail(index)
            if (response.status == "OK") {
                val data = response.data
                return Result.Success(data?.get(0))
            } else {
                return Result.Error(response.message.toString())
            }

        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var INSTACE: FoodDetailRepository? = null

        fun getInstance(
            apiService: ApiService
        ): FoodDetailRepository =
            INSTACE ?: synchronized(this) {
                INSTACE ?: FoodDetailRepository(apiService)
            }.also { INSTACE = it }
    }
}
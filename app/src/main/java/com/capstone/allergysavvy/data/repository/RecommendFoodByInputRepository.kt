package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.response.DataItemFoodRecommendationDetail
import com.capstone.allergysavvy.data.retrofit.ApiService

class RecommendFoodByInputRepository(
    private val apiService: ApiService
) {
    suspend fun getRecommendFoodByInput(ingredient: String): Result<List<DataItemFoodRecommendationDetail?>> {
        try {
            val response = apiService.getRecommendFoodByInput(ingredient)
            with(response) {
                return Result.Success(data ?: emptyList())
            }
        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var INSTACE: RecommendFoodByInputRepository? = null

        fun getInstance(
            apiService: ApiService
        ): RecommendFoodByInputRepository =
            INSTACE ?: synchronized(this) {
                INSTACE ?: RecommendFoodByInputRepository(apiService)
            }.also { INSTACE = it }
    }
}
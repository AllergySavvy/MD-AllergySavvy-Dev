package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.response.RecommendFoodByInputResponse
import com.capstone.allergysavvy.data.retrofit.ApiService

class RecommendFoodByInputRepository(
    private val apiService: ApiService
) {
    suspend fun getRecommendFoodByInput(ingredient: String): RecommendFoodByInputResponse {
        return apiService.getRecommendFoodByInput(ingredient)
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
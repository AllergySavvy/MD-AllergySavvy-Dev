package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.response.SetUserAllergiesResponse
import com.capstone.allergysavvy.data.retrofit.ApiService

class SetUserAllergiesRepository(
    private val apiService: ApiService
) {
    suspend fun setUserAllergies(allergies: String): SetUserAllergiesResponse {
        return apiService.userAllergies(allergies)
    }

    companion object {
        @Volatile
        private var INSTANCE: SetUserAllergiesRepository? = null

        fun getInstance(
            apiService: ApiService
        ): SetUserAllergiesRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SetUserAllergiesRepository(apiService)
            }.also { INSTANCE = it }
    }
}
package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.response.UserDataResponse
import com.capstone.allergysavvy.data.retrofit.ApiService

class GetUserDataRepository(
    private val apiService: ApiService
) {
    suspend fun getUserData(): UserDataResponse {
        return apiService.getUserData()
    }

    companion object {
        @Volatile
        private var INSTANCE: GetUserDataRepository? = null

        fun getInstance(
            apiService: ApiService
        ): GetUserDataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: GetUserDataRepository(apiService)
            }.also { INSTANCE = it }
    }
}
package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.response.LoginResponse
import com.capstone.allergysavvy.data.retrofit.ApiService

class LoginRepository(
    private val apiService: ApiService
) {
    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.loginUser(email = email, password = password)
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginRepository? = null

        fun getInstance(
            apiService: ApiService
        ): LoginRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LoginRepository(apiService)
            }.also { INSTANCE = it }
    }
}
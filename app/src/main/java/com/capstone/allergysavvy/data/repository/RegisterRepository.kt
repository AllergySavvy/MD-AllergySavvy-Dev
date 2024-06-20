package com.capstone.allergysavvy.data.repository

import com.capstone.allergysavvy.data.response.RegisterResponse
import com.capstone.allergysavvy.data.retrofit.ApiService

class RegisterRepository(
    private val apiService: ApiService
) {

    suspend fun registerUser(username: String, email: String, password: String): RegisterResponse {
        return apiService.registerUser(username = username, email = email, password = password)
    }

    companion object {
        @Volatile
        private var INSTANCE: RegisterRepository? = null

        fun getInstance(
            apiService: ApiService
        ): RegisterRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RegisterRepository(apiService)
            }.also { INSTANCE = it }
    }
}
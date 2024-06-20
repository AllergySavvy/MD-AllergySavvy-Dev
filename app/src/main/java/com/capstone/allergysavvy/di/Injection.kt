package com.capstone.allergysavvy.di

import android.content.Context
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.data.repository.LoginRepository
import com.capstone.allergysavvy.data.repository.VideoRepository
import com.capstone.allergysavvy.data.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking

object Injection {
    fun videoRepository(): VideoRepository {
        val apiService = ApiConfig.getApiServiceYoutube()
        return VideoRepository.getInstance(apiService)
    }

    fun loginRepository(context: Context): LoginRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        return LoginRepository.getInstance(apiService)
    }
}
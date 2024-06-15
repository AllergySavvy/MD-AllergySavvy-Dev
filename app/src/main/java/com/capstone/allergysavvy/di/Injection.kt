package com.capstone.allergysavvy.di

import com.capstone.allergysavvy.data.repository.VideoRepository
import com.capstone.allergysavvy.data.retrofit.ApiConfig

object Injection {
    fun videoRepository(): VideoRepository {
        val apiService = ApiConfig.getApiServiceYoutube()
        return VideoRepository.getInstance(apiService)

    }
}
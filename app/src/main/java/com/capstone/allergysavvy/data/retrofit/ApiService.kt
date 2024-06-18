package com.capstone.allergysavvy.data.retrofit

import com.capstone.allergysavvy.data.response.SearchListResponse
import com.capstone.allergysavvy.data.response.VideoDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("youtube/v3/search")
    suspend fun searchVideos(
        @Query("part") part: String,
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String
    ): SearchListResponse

    @GET("youtube/v3/videos")
    suspend fun getVideoDetails(
        @Query("part") part: String,
        @Query("id") id: String,
        @Query("key") apiKey: String
    ): VideoDetailsResponse
}
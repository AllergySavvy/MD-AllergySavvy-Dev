package com.capstone.allergysavvy.data.retrofit

import com.capstone.allergysavvy.data.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("youtube/v3/search")
    fun searchVideos(
        @Query("part") part: String,
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int
    ): Call<SearchListResponse>

    @GET("youtube/v3/videos")
    fun getVideoDetails(
        @Query("part") part: String,
        @Query("id") id: String,
        @Query("key") apiKey: String
    ): Call<VideoDetailsResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("ingredient")
    suspend fun getIngredient(): ShowAllIngredientResponse

    @GET("ingredient/random")
    suspend fun getRandomIngredient(): ShowRandomIngredientResponse

    @GET("user")
    suspend fun getUserData(): UserDataResponse

    @FormUrlEncoded
    @POST("user/allergies")
    suspend fun userAllergies(
        @Field("user_allergies") userAllergies: String
    ): UserDataResponse

    @GET("food/{id}")
    suspend fun getFoodRecipesDetail(
        @Path("id") id: String
    ): FoodDetailResponse

    @GET("food/random")
    suspend fun getFoodRandom(): GetFoodRandomResponse
}

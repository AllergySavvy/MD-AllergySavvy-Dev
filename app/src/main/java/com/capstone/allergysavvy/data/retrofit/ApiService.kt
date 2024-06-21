package com.capstone.allergysavvy.data.retrofit

import com.capstone.allergysavvy.data.response.FoodDetailResponse
import com.capstone.allergysavvy.data.response.GetFoodRandomResponse
import com.capstone.allergysavvy.data.response.LoginResponse
import com.capstone.allergysavvy.data.response.RecommendFoodByInputResponse
import com.capstone.allergysavvy.data.response.RegisterResponse
import com.capstone.allergysavvy.data.response.SearchListResponse
import com.capstone.allergysavvy.data.response.SetUserAllergiesResponse
import com.capstone.allergysavvy.data.response.ShowAllIngredientResponse
import com.capstone.allergysavvy.data.response.ShowRandomIngredientResponse
import com.capstone.allergysavvy.data.response.UserDataResponse
import com.capstone.allergysavvy.data.response.VideoDetailsResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    ): SetUserAllergiesResponse

    @GET("food/{index}")
    suspend fun getFoodRecipesDetail(
        @Path("index") index: Int
    ): FoodDetailResponse

    @GET("food/random")
    suspend fun getFoodRandom(): GetFoodRandomResponse

    @FormUrlEncoded
    @POST("recommendation")
    suspend fun getRecommendFoodByInput(
        @Field("ingredient") ingredient: String
    ): RecommendFoodByInputResponse

}

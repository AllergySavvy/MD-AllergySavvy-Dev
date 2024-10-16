package com.capstone.allergysavvy.di

import android.content.Context
import com.capstone.allergysavvy.data.local.database.RecipeFavoriteDatabase
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.data.repository.FavoriteRepository
import com.capstone.allergysavvy.data.repository.FoodDetailRepository
import com.capstone.allergysavvy.data.repository.FoodRecipeRandomRepository
import com.capstone.allergysavvy.data.repository.GetUserDataRepository
import com.capstone.allergysavvy.data.repository.IngredientRepository
import com.capstone.allergysavvy.data.repository.LoginRepository
import com.capstone.allergysavvy.data.repository.RandomIngredientRepository
import com.capstone.allergysavvy.data.repository.RecommendFoodByInputRepository
import com.capstone.allergysavvy.data.repository.RegisterRepository
import com.capstone.allergysavvy.data.repository.SetUserAllergiesRepository
import com.capstone.allergysavvy.data.repository.VideoRepository
import com.capstone.allergysavvy.data.retrofit.ApiConfig
import com.capstone.allergysavvy.utils.AppExecutors
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

    fun registerRepository(context: Context): RegisterRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        return RegisterRepository.getInstance(apiService)
    }

    fun randomIngredientRepository(context: Context): RandomIngredientRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        return RandomIngredientRepository.getInstance(apiService)
    }

    fun foodRecipeRandomRepository(context: Context): FoodRecipeRandomRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        return FoodRecipeRandomRepository.getInstance(apiService)
    }

    fun ingredientRepository(context: Context): IngredientRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        return IngredientRepository.getInstance(apiService)
    }

    fun getUserDataRepository(context: Context): GetUserDataRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        return GetUserDataRepository.getInstance(apiService)
    }

    fun foodDetailRepository(context: Context): FoodDetailRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        val database = RecipeFavoriteDatabase.getInstance(context)
        val recipeFavoriteDao = database.recipeFavoriteDao()
        val appExecutors = AppExecutors()
        return FoodDetailRepository.getInstance(apiService, appExecutors, recipeFavoriteDao)
    }

    fun userPreference(context: Context): UserPreference {
        return UserPreference.getInstance(context.dataStore)
    }

    fun setUserAllergiesRepository(context: Context): SetUserAllergiesRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        return SetUserAllergiesRepository.getInstance(apiService)
    }

    fun recommendFoodByInputRepository(context: Context): RecommendFoodByInputRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken() }
        val apiService = ApiConfig.getApiService(user)
        return RecommendFoodByInputRepository.getInstance(apiService)
    }

    fun favoriteRepository(context: Context): FavoriteRepository {
        val database = RecipeFavoriteDatabase.getInstance(context)
        val recipeFavoriteDao = database.recipeFavoriteDao()
        return FavoriteRepository.getInstance(recipeFavoriteDao)
    }
}
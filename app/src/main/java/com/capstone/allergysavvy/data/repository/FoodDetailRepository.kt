package com.capstone.allergysavvy.data.repository

import androidx.lifecycle.LiveData
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.local.database.RecipeFavoriteDao
import com.capstone.allergysavvy.data.local.database.RecipeFavoriteEntity
import com.capstone.allergysavvy.data.response.DataItemFoodDetail
import com.capstone.allergysavvy.data.retrofit.ApiService
import com.capstone.allergysavvy.utils.AppExecutors

class FoodDetailRepository private constructor(
    private val apiService: ApiService,
    private val appExecutors: AppExecutors,
    private val foodRecipeFavoriteDao: RecipeFavoriteDao
) {

    suspend fun getDetailFoodRecipe(index: Int): Result<DataItemFoodDetail?> {
        try {
            val response = apiService.getFoodRecipesDetail(index)
            if (response.status == "OK") {
                val data = response.data
                return Result.Success(data?.get(0))
            } else {
                return Result.Error(response.message.toString())
            }

        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }

    fun setFavoriteRecipe(foodRecipeFavoriteEntity: RecipeFavoriteEntity) {
        appExecutors.diskIO.execute {
            foodRecipeFavoriteDao.addFavoriteRecipe(foodRecipeFavoriteEntity)
        }
    }

    fun removeFavoriteRecipe(foodRecipeFavoriteEntity: RecipeFavoriteEntity) {
        appExecutors.diskIO.execute {
            foodRecipeFavoriteDao.removeFavoriteRecipe(foodRecipeFavoriteEntity)
        }
    }

    fun getFavoriteRecipe(index: Int): LiveData<RecipeFavoriteEntity> {
        return foodRecipeFavoriteDao.getRecipeFavoriteByIndex(index)
    }

    companion object {
        @Volatile
        private var INSTACE: FoodDetailRepository? = null

        fun getInstance(
            apiService: ApiService,
            appExecutors: AppExecutors,
            foodRecipeFavoriteDao: RecipeFavoriteDao
        ): FoodDetailRepository =
            INSTACE ?: synchronized(this) {
                INSTACE ?: FoodDetailRepository(
                    apiService,
                    appExecutors,
                    foodRecipeFavoriteDao
                )
            }.also { INSTACE = it }
    }
}
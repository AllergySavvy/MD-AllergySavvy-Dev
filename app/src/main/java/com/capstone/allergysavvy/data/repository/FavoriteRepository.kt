package com.capstone.allergysavvy.data.repository

import androidx.lifecycle.LiveData
import com.capstone.allergysavvy.data.local.database.RecipeFavoriteDao
import com.capstone.allergysavvy.data.local.database.RecipeFavoriteEntity

class FavoriteRepository(
    private val recipeFavoriteDao: RecipeFavoriteDao
) {
    fun getFavoriteRecipes(): LiveData<List<RecipeFavoriteEntity>> {
        return recipeFavoriteDao.getAllFavoriteRecipes()
    }

    companion object {
        @Volatile
        private var INSTACE: FavoriteRepository? = null

        fun getInstance(
            recipeFavoriteDao: RecipeFavoriteDao
        ): FavoriteRepository =
            INSTACE ?: synchronized(this) {
                INSTACE ?: FavoriteRepository(recipeFavoriteDao)
            }.also { INSTACE = it }
    }
}
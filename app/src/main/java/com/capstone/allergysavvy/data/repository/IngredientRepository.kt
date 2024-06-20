package com.capstone.allergysavvy.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.capstone.allergysavvy.data.paging.IngredientPagingSource
import com.capstone.allergysavvy.data.response.DataItemIngredient
import com.capstone.allergysavvy.data.retrofit.ApiService

class IngredientRepository private constructor(
    private val apiService: ApiService
) {

    fun getIngredient(): LiveData<PagingData<DataItemIngredient>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                IngredientPagingSource(apiService)
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var INSTANCE: IngredientRepository? = null

        fun getInstance(
            apiService: ApiService
        ): IngredientRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: IngredientRepository(apiService)
            }.also { INSTANCE = it }
    }
}
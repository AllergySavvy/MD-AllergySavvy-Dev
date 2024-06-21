package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.repository.RecommendFoodByInputRepository
import com.capstone.allergysavvy.data.response.DataItemFoodRecommendationDetail
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class RecipeViewModel(
    private val recommendFoodByInputRepository: RecommendFoodByInputRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _recommendFoodByInput =
        MutableLiveData<Result<List<DataItemFoodRecommendationDetail?>>>()
    val recommendFoodByInput: LiveData<Result<List<DataItemFoodRecommendationDetail?>>>
        get() = _recommendFoodByInput

    fun getRecommendFoodByInput(ingredient: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val result = recommendFoodByInputRepository.getRecommendFoodByInput(ingredient)
                _recommendFoodByInput.value = result
            } catch (e: SocketTimeoutException) {
                _isLoading.postValue(false)
                _recommendFoodByInput.postValue(Result.Error("Request timed out"))
                Log.e("RecipeViewModel", "Timeout Error: ${e.message}")
            } catch (e: Exception) {
                _isLoading.postValue(false)
                _recommendFoodByInput.postValue(Result.Error(e.message ?: "Unknown Error"))
                Log.e("RecipeViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

}


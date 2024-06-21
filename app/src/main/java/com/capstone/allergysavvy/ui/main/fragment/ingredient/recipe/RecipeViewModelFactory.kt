package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.repository.RecommendFoodByInputRepository

class RecipeViewModelFactory private constructor(
    private val recommendFoodByInputRepository: RecommendFoodByInputRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(recommendFoodByInputRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTACE: RecipeViewModelFactory? = null

        fun getInstance(
            recommendFoodByInputRepository: RecommendFoodByInputRepository
        ): RecipeViewModelFactory =
            INSTACE ?: synchronized(this) {
                INSTACE ?: RecipeViewModelFactory(recommendFoodByInputRepository)
            }.also { INSTACE = it }
    }
}
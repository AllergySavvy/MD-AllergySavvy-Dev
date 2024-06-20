package com.capstone.allergysavvy.ui.main.fragment.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.repository.FoodRecipeRandomRepository
import com.capstone.allergysavvy.data.repository.GetUserDataRepository
import com.capstone.allergysavvy.data.repository.RandomIngredientRepository
import com.capstone.allergysavvy.di.Injection

class HomeViewModelFactory private constructor(
    private val userDataRepository: GetUserDataRepository,
    private val foodRecipeRandomRepository: FoodRecipeRandomRepository,
    private val ingredientRandomRepository: RandomIngredientRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                userDataRepository,
                foodRecipeRandomRepository,
                ingredientRandomRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown view model clas" + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeViewModelFactory? = null

        fun getInstance(
            context: Context,
        ): HomeViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomeViewModelFactory(
                    Injection.getUserDataRepository(context),
                    Injection.foodRecipeRandomRepository(context),
                    Injection.randomIngredientRepository(context)
                )
            }.also { INSTANCE = it }
    }

}
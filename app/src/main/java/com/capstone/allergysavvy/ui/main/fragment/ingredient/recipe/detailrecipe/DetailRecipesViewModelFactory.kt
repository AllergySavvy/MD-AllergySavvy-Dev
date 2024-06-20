package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailrecipe

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.repository.FoodDetailRepository
import com.capstone.allergysavvy.di.Injection

class DetailRecipesViewModelFactory(
    private val foodDetailRepository: FoodDetailRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailRecipeViewModel::class.java)) {
            return DetailRecipeViewModel(foodDetailRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }

    companion object {

        @Volatile
        private var INSTACE: DetailRecipesViewModelFactory? = null

        fun getInstance(
            context: Context
        ): DetailRecipesViewModelFactory =
            INSTACE ?: synchronized(this) {
                INSTACE ?: DetailRecipesViewModelFactory(
                    Injection.foodDetailRepository(context)
                )
            }.also { INSTACE = it }
    }
}
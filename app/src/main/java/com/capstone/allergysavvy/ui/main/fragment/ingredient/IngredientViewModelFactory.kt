package com.capstone.allergysavvy.ui.main.fragment.ingredient

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.repository.IngredientRepository
import com.capstone.allergysavvy.di.Injection
import com.capstone.allergysavvy.ui.main.fragment.home.HomeViewModel

class IngredientViewModelFactory(
    private val ingredientRepository: IngredientRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientViewModel::class.java)) {
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: IngredientViewModelFactory? = null

        fun getInstance(
            context: Context
        ): IngredientViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: IngredientViewModelFactory(
                    Injection.ingredientRepository(context)
                )
            }.also { INSTANCE = it }
    }
}
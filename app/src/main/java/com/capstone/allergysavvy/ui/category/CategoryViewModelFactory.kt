package com.capstone.allergysavvy.ui.category

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.di.Injection

class CategoryViewModelFactory(
    private val userPreference: UserPreference
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: CategoryViewModelFactory? = null

        fun getInstance(context: Context)
                : CategoryViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CategoryViewModelFactory(
                    Injection.userPreference(context)
                )
            }.also { INSTANCE = it }
    }
}
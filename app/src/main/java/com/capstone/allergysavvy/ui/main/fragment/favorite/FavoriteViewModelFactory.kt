package com.capstone.allergysavvy.ui.main.fragment.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.repository.FavoriteRepository
import com.capstone.allergysavvy.di.Injection

class FavoriteViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown view model class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTACE: FavoriteViewModelFactory? = null

        fun getInstance(context: Context): FavoriteViewModelFactory =
            INSTACE ?: synchronized(this) {
                INSTACE ?: FavoriteViewModelFactory(
                    Injection.favoriteRepository(context)
                )
            }.also { INSTACE = it }
    }
}
package com.capstone.allergysavvy.ui.splashscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.repository.GetUserDataRepository
import com.capstone.allergysavvy.di.Injection

class SplashViewModelFactory(
    private val userPreference: UserPreference,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(userPreference) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SplashViewModelFactory? = null

        fun getInstance(
            userPreference: UserPreference
        ): SplashViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SplashViewModelFactory(
                    userPreference,
                )
            }.also { INSTANCE = it }
    }

}
package com.capstone.allergysavvy.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.repository.GetUserDataRepository
import com.capstone.allergysavvy.data.repository.LoginRepository
import com.capstone.allergysavvy.di.Injection

class LoginViewModelFactory private constructor(
    private val application: Application,
    private val loginRepository: LoginRepository,
    private val userDataRepository: GetUserDataRepository,
    private val userPreference: UserPreference
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                application,
                loginRepository,
                userDataRepository,
                userPreference
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginViewModelFactory? = null

        fun getInstance(
            application: Application,
            userPreference: UserPreference
        ): LoginViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LoginViewModelFactory(
                    application,
                    Injection.loginRepository(application),
                    Injection.getUserDataRepository(application),
                    userPreference
                )
            }.also { INSTANCE = it }
    }
}
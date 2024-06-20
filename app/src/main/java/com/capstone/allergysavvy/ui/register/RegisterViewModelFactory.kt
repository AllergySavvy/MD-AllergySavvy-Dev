package com.capstone.allergysavvy.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.repository.RegisterRepository
import com.capstone.allergysavvy.di.Injection

class RegisterViewModelFactory private constructor(
    private val application: Application,
    private val registerRepository: RegisterRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(application, registerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: RegisterViewModelFactory? = null

        fun getInstance(application: Application): RegisterViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RegisterViewModelFactory(
                    application,
                    Injection.registerRepository(application)
                )
            }.also { INSTANCE = it }
    }
}
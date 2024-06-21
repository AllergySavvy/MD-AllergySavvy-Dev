package com.capstone.allergysavvy.ui.category.form

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.repository.SetUserAllergiesRepository
import com.capstone.allergysavvy.di.Injection

class FormViewModelFactory private constructor(
    private val application: Application,
    private val setUserAllergiesRepository: SetUserAllergiesRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            return FormViewModel(application, setUserAllergiesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTACE: FormViewModelFactory? = null

        fun getInstance(application: Application): FormViewModelFactory =
            INSTACE ?: synchronized(this) {
                INSTACE ?: FormViewModelFactory(
                    application,
                    Injection.setUserAllergiesRepository(application)
                )
            }.also { INSTACE = it }


    }
}
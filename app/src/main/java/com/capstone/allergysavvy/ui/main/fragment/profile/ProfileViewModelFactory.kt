package com.capstone.allergysavvy.ui.main.fragment.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.local.pref.dataStore

class ProfileViewModelFactory(
    private val userPreference: UserPreference
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userPreference) as T
        }
        throw IllegalArgumentException("View model class unknown" + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTACE: ProfileViewModelFactory? = null

        fun getInstance(
            context: Context
        ): ProfileViewModelFactory =
            INSTACE ?: synchronized(this) {
                INSTACE ?: ProfileViewModelFactory(
                    UserPreference.getInstance(context.dataStore)
                )
            }.also { INSTACE = it }
    }
}
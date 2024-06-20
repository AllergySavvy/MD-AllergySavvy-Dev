package com.capstone.allergysavvy.ui.main.fragment.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.local.pref.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPreference: UserPreference
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            userPreference.clearUserToken()
        }
    }
}
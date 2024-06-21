package com.capstone.allergysavvy.ui.main.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.local.pref.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPreference: UserPreference
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    init {
        getUserName()
    }

    private fun getUserName() {
        viewModelScope.launch {
            _userName.value = userPreference.getUserName()
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreference.clearUserData()
        }
    }
}
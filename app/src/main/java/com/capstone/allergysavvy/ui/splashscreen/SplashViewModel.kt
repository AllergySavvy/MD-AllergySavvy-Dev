package com.capstone.allergysavvy.ui.splashscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.repository.GetUserDataRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreference: UserPreference,
) : ViewModel() {

    private val _isUserTokenAvailable = MutableLiveData<Boolean>()
    val isUserTokenAvailable: LiveData<Boolean>
        get() = _isUserTokenAvailable

    init {
        checkUserData()
    }

    private fun checkUserData() {
        viewModelScope.launch {
            val token = userPreference.getUserToken()
            _isUserTokenAvailable.value = token.isNotEmpty()
        }
    }
}

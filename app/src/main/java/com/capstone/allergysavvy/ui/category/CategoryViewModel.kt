package com.capstone.allergysavvy.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.local.pref.UserPreference
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val userPreference: UserPreference
) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _username

    init {
        getUserName()
    }

    private fun getUserName() {
        viewModelScope.launch {
            _username.value = userPreference.getUserName()
        }
    }
}
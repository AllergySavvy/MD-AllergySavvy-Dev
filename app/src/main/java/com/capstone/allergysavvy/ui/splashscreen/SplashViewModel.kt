package com.capstone.allergysavvy.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.repository.GetUserDataRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreference: UserPreference,
    private val getUserDataRepository: GetUserDataRepository
) : ViewModel() {

    private val _isUserTokenAvailable = MutableLiveData<Boolean>()

    val isUserTokenAvailable: LiveData<Boolean>
        get() = _isUserTokenAvailable

    private val _isUserAllergyStatus = MutableLiveData<Boolean>()
    val isUserAllergy: LiveData<Boolean>
        get() = _isUserAllergyStatus

    private val _isUserHaveAllergyIngredient = MutableLiveData<Boolean>()
    val isUserHaveAllergyIngredient: LiveData<Boolean>
        get() = _isUserHaveAllergyIngredient

    init {
        checkUserData()
        getUserData()
    }

    private fun checkUserData() {
        viewModelScope.launch {
            val token = userPreference.getUserToken()
            _isUserTokenAvailable.value = token.isNotEmpty()

            val isUserAllergy = userPreference.getStatusAllergyUser()
            _isUserAllergyStatus.value = isUserAllergy
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            val response = getUserDataRepository.getUserData()
            val userAllergy = response.data?.userAllergies
            _isUserHaveAllergyIngredient.value = userAllergy != null

        }
    }

}
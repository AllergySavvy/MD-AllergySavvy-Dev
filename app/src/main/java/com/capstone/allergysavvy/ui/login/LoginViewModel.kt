package com.capstone.allergysavvy.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.repository.LoginRepository
import kotlinx.coroutines.launch
import org.apache.http.HttpException

class LoginViewModel(
    application: Application,
    private val loginRepository: LoginRepository,
    private val userPreference: UserPreference
) : AndroidViewModel(application) {

    private val _showSuccessDialog = MutableLiveData<String>()
    val showSuccessDialog: LiveData<String>
        get() = _showSuccessDialog

    private val _showErrorDialog = MutableLiveData<String>()
    val showErrorDialog: LiveData<String>
        get() = _showErrorDialog

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun loginUser(email: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = loginRepository.login(email, password)
                val loginResult = response.data
                val userToken = response.token
                _loading.postValue(false)
                _showSuccessDialog.postValue(response.message.toString())
                userToken?.let { token ->
                    saveUserData(token)
                }
            } catch (e: Exception) {
                _loading.postValue(false)
                val errorMessage = e.message
                _showErrorDialog.postValue(errorMessage.toString())
            } catch (e: HttpException) {
                _loading.postValue(false)
                val errorMessage = e.message
                _showErrorDialog.postValue(errorMessage.toString())
            }
        }
    }

    private fun saveUserData(userToken: String) {
        viewModelScope.launch {
            userPreference.saveUserToken(userToken)
        }
    }

}
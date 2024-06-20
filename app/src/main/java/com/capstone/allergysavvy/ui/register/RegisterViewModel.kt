package com.capstone.allergysavvy.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.http.HttpException

class RegisterViewModel(
    application: Application,
    private val registerRepository: RegisterRepository
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

    fun registerUser(name: String, email: String, password: String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                registerRepository.registerUser(username = name, email = email, password = password)
                _loading.postValue(false)
                _showSuccessDialog.postValue("Registration successful")
            } catch (e: Exception) {
                _loading.postValue(false)
                _showErrorDialog.postValue(e.message)
            } catch (e: HttpException) {
                _loading.postValue(false)
                _showErrorDialog.postValue(e.message)
            }
        }
    }

}
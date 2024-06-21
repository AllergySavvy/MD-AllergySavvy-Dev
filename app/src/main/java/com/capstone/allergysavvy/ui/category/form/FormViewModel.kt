package com.capstone.allergysavvy.ui.category.form

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.repository.SetUserAllergiesRepository
import kotlinx.coroutines.launch
import org.apache.http.HttpException

class FormViewModel(
    application: Application,
    private val setUserAllergiesRepository: SetUserAllergiesRepository
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

    fun setUserAllergies(allergies: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = setUserAllergiesRepository.setUserAllergies(allergies)
                _loading.postValue(false)
                _showSuccessDialog.postValue(response.message.toString())
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
}
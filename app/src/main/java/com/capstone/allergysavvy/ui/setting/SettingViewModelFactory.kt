package com.capstone.allergysavvy.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.local.pref.SettingPreference

class SettingViewModelFactory(
    private val settingPreference: SettingPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(settingPreference) as T
        }
        throw IllegalArgumentException(
            "Viewmodel class unknown " + modelClass.name
        )
    }
}
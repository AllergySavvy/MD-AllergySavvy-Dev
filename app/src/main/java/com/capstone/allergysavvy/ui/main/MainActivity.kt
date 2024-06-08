package com.capstone.allergysavvy.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityMainBinding
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkThemeSetting()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun checkThemeSetting() {
        val settingPreference = SettingPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(settingPreference)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this) { darkModeActive ->
            val mode = when {
                darkModeActive -> AppCompatDelegate.MODE_NIGHT_YES
                !darkModeActive -> {
                    if (isSystemInDarkMode()) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                }

                else -> AppCompatDelegate.MODE_NIGHT_NO
            }
            delegate.localNightMode = mode
        }
    }

    private fun isSystemInDarkMode(): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}
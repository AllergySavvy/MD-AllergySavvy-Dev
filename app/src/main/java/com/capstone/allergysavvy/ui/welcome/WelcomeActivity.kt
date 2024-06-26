package com.capstone.allergysavvy.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityWelcomeBinding
import com.capstone.allergysavvy.ui.login.LoginActivity
import com.capstone.allergysavvy.ui.register.RegisterActivity
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkThemeSetting()


        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
    }

    private fun checkThemeSetting() {
        val settingPreference = SettingPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(settingPreference)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this) { darkModeActive ->
            if (darkModeActive) {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
                binding.ivLogoWelcome.setImageResource(R.drawable.logo_allergysavvy_dark_mode_svg)
            } else {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
                binding.ivLogoWelcome.setImageResource(R.drawable.logo_allergysavvy_svg)
            }
        }
    }

    private fun setupAction() {
        with(binding) {
            btnGoToSignInWelcome.setOnClickListener {
                intent = Intent(it.context, LoginActivity::class.java)
                startActivity(intent)
            }

            btnGoToSignUpWelcome.setOnClickListener {
                intent = Intent(it.context, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
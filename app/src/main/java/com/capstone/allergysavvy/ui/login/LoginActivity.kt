package com.capstone.allergysavvy.ui.login

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityLoginBinding
import com.capstone.allergysavvy.ui.category.CategoryActivity
import com.capstone.allergysavvy.ui.register.RegisterActivity
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkThemeSetting()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factoryResult: LoginViewModelFactory = LoginViewModelFactory.getInstance(application)
        loginViewModel = ViewModelProvider(this, factoryResult)[LoginViewModel::class.java]

        setupAction()
        textObserver()
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

    private fun setupAction() {
        with(binding) {
            btnGoToRegister.setOnClickListener {
                intent = Intent(it.context, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnLogin.setOnClickListener {
                validateInput()
            }
        }
    }

    private fun validateInput() {
        val email = binding.edEmailLogin.text.toString()
        val password = binding.edPasswordLogin.text.toString()
        val emailPattern = getString(R.string.email_pattern)
        when {
            password.isEmpty() -> binding.tiPasswordLogin.error =
                getString(R.string.password_cannot_be_empty)

            password.length < 8 -> binding.tiPasswordLogin.error =
                getString(R.string.password_must_be_at_least_8_characters)

            email.isEmpty() -> binding.tiEmailLogin.error =
                getString(R.string.email_cannot_be_empty)

            !email.matches(Regex(emailPattern)) -> binding.tiEmailLogin.error =
                getString(R.string.invalid_email_format)

            else -> with(binding) {
                tiPasswordLogin.error = null
                tiEmailLogin.error = null
                loginUser(email, password)
            }
        }
    }

    private fun textObserver() {
        binding.edEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tiEmailLogin.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tiPasswordLogin.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loginUser(email: String, password: String) {
        val intent = Intent(this@LoginActivity, CategoryActivity::class.java)
        startActivity(intent)
        finish()
    }
}
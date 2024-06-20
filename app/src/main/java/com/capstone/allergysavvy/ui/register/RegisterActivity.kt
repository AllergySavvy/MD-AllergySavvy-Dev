package com.capstone.allergysavvy.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityRegisterBinding
import com.capstone.allergysavvy.ui.login.LoginActivity
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkThemeSetting()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factoryResult: RegisterViewModelFactory =
            RegisterViewModelFactory.getInstance(application)

        registerViewModel = ViewModelProvider(
            this,
            factoryResult
        )[RegisterViewModel::class.java]


        setupAction()
        viewModelObserver()
        textObserver()
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
            } else {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
            }
        }
    }

    private fun setupAction() {
        with(binding) {
            btnGoToLogin.setOnClickListener {
                intent = Intent(it.context, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnRegister.setOnClickListener {
                validateInput()
            }
        }
    }

    private fun validateInput() {
        val username = binding.edUsernameRegister.text.toString()
        val password = binding.edPasswordRegister.text.toString()
        val email = binding.edEmailRegister.text.toString()
        val emailPattern = getString(R.string.email_pattern)
        when {
            username.isEmpty() -> binding.tiUsernameRegister.error =
                getString(R.string.username_cannot_be_empty)

            password.isEmpty() -> binding.tiPasswordRegister.error =
                getString(R.string.password_cannot_be_empty)

            password.length < 8 -> binding.tiPasswordRegister.error =
                getString(R.string.password_must_be_at_least_8_characters)

            email.isEmpty() -> binding.tiEmailRegister.error =
                getString(R.string.email_cannot_be_empty)

            !email.matches(Regex(emailPattern)) -> binding.tiEmailRegister.error =
                getString(R.string.invalid_email_format)

            else -> with(binding) {
                tiPasswordRegister.error = null
                tiEmailRegister.error = null
                tiUsernameRegister.error = null
                registerUser(username, email, password)
            }
        }
    }

    private fun viewModelObserver() {
        registerViewModel.showSuccessDialog.observe(this) {
            showDialog(it)
        }

        registerViewModel.showErrorDialog.observe(this) {
            showErrorDialog(it)
        }
    }

    private fun textObserver() {
        binding.edEmailRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tiEmailRegister.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edPasswordRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tiPasswordRegister.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edUsernameRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tiUsernameRegister.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    @Suppress("DEPRECATION")
    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Confirm") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()

        val messageView = alertDialog.findViewById<TextView>(android.R.id.message)
        messageView?.setTextColor(resources.getColor(R.color.black))
        alertDialog.window?.setBackgroundDrawableResource(R.color.white)
    }

    @Suppress("DEPRECATION")
    private fun showErrorDialog(errorMessage: String) {
        val builder = AlertDialog.Builder(this)
            .setMessage(errorMessage)
            .setPositiveButton("Confirm") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = builder.create()
        alertDialog.show()
        val messageView = alertDialog.findViewById<TextView>(android.R.id.message)
        messageView?.setTextColor(resources.getColor(R.color.black))
        alertDialog.window?.setBackgroundDrawableResource(R.color.white)
    }


    private fun registerUser(username: String, email: String, password: String) {
        registerViewModel.registerUser(name = username, email = email, password = password)
    }
}
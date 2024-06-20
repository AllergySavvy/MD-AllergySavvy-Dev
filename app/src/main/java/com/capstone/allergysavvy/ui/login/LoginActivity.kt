package com.capstone.allergysavvy.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
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
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityLoginBinding
import com.capstone.allergysavvy.ui.main.MainActivity
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

        val userPref = UserPreference.getInstance(application.dataStore)
        val factoryResult: LoginViewModelFactory =
            LoginViewModelFactory.getInstance(application, userPref)
        loginViewModel = ViewModelProvider(this, factoryResult)[LoginViewModel::class.java]

        setupAction()
        textObserver()
        observeViewModel()
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

    private fun observeViewModel() {
        loginViewModel.showSuccessDialog.observe(this) {
            showDialog(it)
        }

        loginViewModel.showErrorDialog.observe(this) {
            showErrorDialog(it)
        }

        loginViewModel.loading.observe(this) {
            if (it) {
                binding.progressBarLogin.visibility = View.VISIBLE
                binding.overlayLogin.visibility = View.VISIBLE
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else {
                binding.progressBarLogin.visibility = View.GONE
                binding.overlayLogin.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Confirm") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
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

    private fun loginUser(email: String, password: String) {
        loginViewModel.loginUser(email, password)
    }
}
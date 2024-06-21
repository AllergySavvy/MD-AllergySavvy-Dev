package com.capstone.allergysavvy.ui.category.form

import android.content.Intent
import android.os.Bundle
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
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityFormBinding
import com.capstone.allergysavvy.ui.main.MainActivity
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory

class FormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding
    private lateinit var formViewModel: FormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factoryResult: FormViewModelFactory =
            FormViewModelFactory.getInstance(application)
        formViewModel = ViewModelProvider(this, factoryResult)[FormViewModel::class.java]

        checkThemeSetting()
        setupAction()
        observerViewModel()
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

    private fun observerViewModel() {
        formViewModel.showSuccessDialog.observe(this) {
            showDialog(it)
        }

        formViewModel.showErrorDialog.observe(this) {
            showErrorDialog(it)
        }

        formViewModel.loading.observe(this) {
            if (it) {
                binding.progressBarForm.visibility = View.VISIBLE
                binding.overlayForm.visibility = View.VISIBLE
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else {
                binding.progressBarForm.visibility = View.GONE
                binding.overlayForm.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
    }

    private fun setupAction() {
        binding.btnSendIngredientForm.setOnClickListener {
            validateInput()
        }
    }

    private fun validateInput() {
        val ingredient = binding.edAllergyIngredient1.text.toString()

        when {
            ingredient.isEmpty() -> {
                binding.tiAllergyIngredient1.error = "Please enter ingredient here"
            }

            else -> with(binding) {
                tiAllergyIngredient1.error = null
                showDialogConfirmation(ingredient)
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

    private fun showDialogConfirmation(
        ingredient: String
    ) {
        AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to submit your allergy food ingredients?\n\n${ingredient}")
            .setPositiveButton("Confirm") { _, _ ->
                inputIngredient(ingredient)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }


    private fun inputIngredient(ingredient: String) {
        formViewModel.setUserAllergies(ingredient)
    }
}
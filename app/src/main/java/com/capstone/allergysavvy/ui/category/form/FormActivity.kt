package com.capstone.allergysavvy.ui.category.form

import android.content.Intent
import android.os.Bundle
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

        checkThemeSetting()
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
            } else {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
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
        val intent = Intent(this@FormActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
package com.capstone.allergysavvy.ui.category.form

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
        val ingredient1 = binding.edAllergyIngredient1.text.toString()
        val ingredient2 = binding.edAllergyIngredient2.text.toString()
        val ingredient3 = binding.edAllergyIngredient3.text.toString()

        when {
            ingredient1.isEmpty() -> {
                binding.tiAllergyIngredient1.error = "Please enter ingredient"
            }

            else -> with(binding) {
                tiAllergyIngredient1.error = null
                tiAllergyIngredient2.error = null
                tiAllergyIngredient3.error = null
                inputIngredient(ingredient1, ingredient2, ingredient3)
            }
        }
    }

    private fun inputIngredient(ingredient1: String, ingredient2: String?, ingredient3: String?) {
        val intent = Intent(this@FormActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
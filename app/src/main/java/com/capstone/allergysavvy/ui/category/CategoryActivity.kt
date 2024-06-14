package com.capstone.allergysavvy.ui.category

import android.content.Intent
import android.content.res.Configuration
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
import com.capstone.allergysavvy.databinding.ActivityCategoryBinding
import com.capstone.allergysavvy.ui.category.form.FormActivity
import com.capstone.allergysavvy.ui.main.MainActivity
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private var isUserAllergy: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
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
        binding.rgCategory.setOnCheckedChangeListener { _, checkedAllergy ->
            when (checkedAllergy) {
                binding.rbAllergyCategory.id -> {
                    isUserAllergy = true
                }

                binding.rbNonAllergyCategory.id -> {
                    isUserAllergy = false
                }

            }
        }

        binding.btnChooseCategory.setOnClickListener {
            if (isUserAllergy) {
                allergyCategory()
            } else {
                nonAllergyCategory()
            }
        }
    }

    private fun allergyCategory() {
        val intent = Intent(this@CategoryActivity, FormActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun nonAllergyCategory() {
        val intent = Intent(this@CategoryActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
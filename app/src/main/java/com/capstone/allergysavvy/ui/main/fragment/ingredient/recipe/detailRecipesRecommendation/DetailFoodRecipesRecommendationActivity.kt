package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailRecipesRecommendation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityDetailRecipeBinding
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory

class DetailFoodRecipesRecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkThemeSetting()

        val imageUrl = intent.getStringExtra(EXTRA_URL_IMAGE)
        val recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME)
        val ingredientsQuantity = intent.getStringExtra(EXTRA_INGREDIENTS)
        val instructions = intent.getStringExtra(EXTRA_INSTRUCTIONS)
        val cookTimeInMins = intent.getStringExtra(EXTRA_COOK_TIME)
        val cuisine = intent.getStringExtra(EXTRA_CUISINE)

        with(binding) {
            tvTitleRecipes.text = recipeName
            tvLocationRecipes.text = cuisine
            tvDurationRecipes.text = cookTimeInMins
            tvIngredientsListDetailRecipes.text = ingredientsQuantity
            tvStepsListDetailRecipes.text = instructions
            Glide.with(binding.root)
                .load(imageUrl)
                .into(ivPhotoRecipes)
        }
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

    companion object {
        const val EXTRA_URL_IMAGE = "extra_url_image"
        const val EXTRA_RECIPE_NAME = "extra_recipe_name"
        const val EXTRA_INGREDIENTS = "extra_ingredients"
        const val EXTRA_INSTRUCTIONS = "extra_instructions"
        const val EXTRA_COOK_TIME = "extra_cook_time"
        const val EXTRA_CUISINE = "extra_cuisine"

    }
}
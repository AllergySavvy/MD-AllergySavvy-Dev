package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityRecipeBinding
import com.capstone.allergysavvy.di.Injection
import com.capstone.allergysavvy.ui.adapter.RecommendationFoodAdapter
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: RecipeViewModelFactory =
            RecipeViewModelFactory.getInstance(Injection.recommendFoodByInputRepository(this))
        recipeViewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        val selectedIngredients = intent.getStringExtra(EXTRA_SELECTED_INGREDIENTS)
        if (selectedIngredients != null) {
            recipeViewModel.getRecommendFoodByInput(selectedIngredients)
        }

        checkThemeSetting()

        recipeViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBarRecipe.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        recipeViewModel.recommendFoodByInput.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressBarRecipe.visibility = View.GONE
                    val data = result.data
                    binding.rvRecipes.layoutManager = LinearLayoutManager(this)
                    binding.rvRecipes.adapter = RecommendationFoodAdapter(data)
                }

                is Result.Error -> {
                    binding.progressBarRecipe.visibility = View.GONE
                    val errorMessage = result.error
                    Snackbar.make(
                        binding.root,
                        errorMessage,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    Log.e("RecipeActivity", "Error: $errorMessage")
                }

                is Result.Loading -> {
                    binding.progressBarRecipe.visibility = View.VISIBLE
                }
            }
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
        const val EXTRA_SELECTED_INGREDIENTS = "extra_selected_ingredients"
    }
}


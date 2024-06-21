package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityRecipeBinding
import com.capstone.allergysavvy.di.Injection
import com.capstone.allergysavvy.ui.adapter.FoodRecipeRandomAdapter
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
            RecipeViewModelFactory.getInstance(
                this,
                Injection.recommendFoodByInputRepository(context = this)
            )
        recipeViewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        binding.rvRecipes.layoutManager = GridLayoutManager(this, 2)
        val foodRecipeRandomAdapter = FoodRecipeRandomAdapter()
        binding.rvRecipes.adapter = foodRecipeRandomAdapter

        checkThemeSetting()

        recipeViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBarRecipe.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        recipeViewModel.findFoodRandom()

        recipeViewModel.foodRecipeRandom.observe(this) {
            when (it) {
                is Result.Error -> {
                    binding.progressBarRecipe.visibility = View.GONE
                    showSnackBar(it.error)
                }

                Result.Loading -> binding.progressBarRecipe.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBarRecipe.visibility = View.GONE
                    (binding.rvRecipes.adapter as FoodRecipeRandomAdapter).submitList(it.data)
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

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val EXTRA_SELECTED_INGREDIENTS = "extra_selected_ingredients"
    }
}

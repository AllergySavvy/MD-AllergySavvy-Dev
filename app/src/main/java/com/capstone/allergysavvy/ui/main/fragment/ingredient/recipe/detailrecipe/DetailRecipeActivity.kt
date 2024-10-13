package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailrecipe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.local.database.RecipeFavoriteEntity
import com.capstone.allergysavvy.data.local.pref.SettingPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.databinding.ActivityDetailRecipeBinding
import com.capstone.allergysavvy.ui.setting.SettingViewModel
import com.capstone.allergysavvy.ui.setting.SettingViewModelFactory
import com.google.android.material.snackbar.Snackbar

class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding
    private lateinit var detailRecipeViewModel: DetailRecipeViewModel
    private var isFavorite = false
    private var imageUrl: String? = null
    private var recipeName: String? = null

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

        val factoryDetailRecipe: DetailRecipesViewModelFactory =
            DetailRecipesViewModelFactory.getInstance(this)

        detailRecipeViewModel = ViewModelProvider(
            this, factoryDetailRecipe
        )[DetailRecipeViewModel::class.java]

        val index = intent.getIntExtra(EXTRA_INDEX, 0)
        if (index > 0) {
            if (detailRecipeViewModel.foodDetail.value == null) {
                detailRecipeViewModel.getFoodDetail(index)
            }
            detailRecipeViewModel.isFavoriteRecipe(index).observe(this) {
                isFavorite = it
                if (isFavorite) {
                    binding.btnFavoriteDetailRecipes.setImageResource(R.drawable.fill_favorite)
                } else {
                    binding.btnFavoriteDetailRecipes.setImageResource(R.drawable.favorite_white)
                }
            }
        }

        setupObserver()
        setupAction()
    }

    @Suppress("DEPRECATION")
    private fun setupAction() {
        binding.btnBackDetailRecipes.setOnClickListener {
            onBackPressed()
        }

        binding.btnFavoriteDetailRecipes.setOnClickListener {
            val index = intent.getIntExtra(EXTRA_INDEX, 0)
            val recipeFavoriteEntity = RecipeFavoriteEntity(index)
            if (isFavorite) {
                detailRecipeViewModel.removeFavoriteRecipe(recipeFavoriteEntity)
                showSnackBar("Removed from favorite")
            } else {
                val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
                val recipeName = intent.getStringExtra(EXTRA_RECIPES_NAME)
                detailRecipeViewModel.setFavoriteRecipe(
                    index = index,
                    imageUrl = imageUrl,
                    recipeName = recipeName
                )
                showSnackBar("Added to favorite")
            }

        }

    }

    private fun setupObserver() {

        detailRecipeViewModel.foodDetail.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Error -> {
                        binding.progressBarDetailFoodRecipe.visibility = View.GONE
                        Snackbar.make(
                            binding.root,
                            "Error: ${result.error}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    Result.Loading -> binding.progressBarDetailFoodRecipe.visibility = View.VISIBLE
                    is Result.Success -> {
                        binding.progressBarDetailFoodRecipe.visibility = View.GONE
                        val foodRecipe = result.data
                        with(binding) {
                            tvTitleRecipes.text = foodRecipe?.recipeName
                            tvLocationRecipes.text = foodRecipe?.cuisine
                            tvDurationRecipes.text = foodRecipe?.cookTimeInMins
                            Glide.with(binding.root)
                                .load(foodRecipe?.imageUrl)
                                .into(ivPhotoRecipes)
                            val recipes = foodRecipe?.instructions
                                ?.joinToString("\n\n") { instructions -> instructions.toString() }
                            tvStepsListDetailRecipes.text = recipes

                            val ingredients = foodRecipe?.ingredientsQuantity
                                ?.filterNot { it?.startsWith("Ingredient list") == true }
                                ?.joinToString("\n") { ingredients -> ingredients.toString() }
                            tvIngredientsListDetailRecipes.text = ingredients
                        }
                    }
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
        const val EXTRA_INDEX = "extra_index"
        const val EXTRA_RECIPES_NAME = "extra_recipes_name"
        const val EXTRA_IMAGE_URL = "extra_image_url"
    }
}
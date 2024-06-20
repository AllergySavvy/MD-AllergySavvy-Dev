package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailrecipe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.databinding.ActivityDetailRecipeBinding
import com.google.android.material.snackbar.Snackbar

class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding
    private lateinit var detailRecipeViewModel: DetailRecipeViewModel

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
        }

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

    companion object {
        const val EXTRA_INDEX = "extra_index"
    }
}
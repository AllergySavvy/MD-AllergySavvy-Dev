package com.capstone.allergysavvy.ui.main.fragment.favorite

import androidx.lifecycle.ViewModel
import com.capstone.allergysavvy.data.repository.FavoriteRepository

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    fun getFavoriteRecipe() = favoriteRepository.getFavoriteRecipes()
}
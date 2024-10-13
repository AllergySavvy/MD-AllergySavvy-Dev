package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailrecipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.local.database.RecipeFavoriteEntity
import com.capstone.allergysavvy.data.repository.FoodDetailRepository
import com.capstone.allergysavvy.data.response.DataItemFoodDetail
import kotlinx.coroutines.launch

class DetailRecipeViewModel(
    private val foodDetailRepository: FoodDetailRepository
) : ViewModel() {
    private val _foodDetail = MutableLiveData<Result<DataItemFoodDetail?>>()
    val foodDetail: LiveData<Result<DataItemFoodDetail?>>
        get() = _foodDetail

    fun getFoodDetail(index: Int) {
        viewModelScope.launch {
            try {
                val result = foodDetailRepository.getDetailFoodRecipe(index)
                _foodDetail.value = result
            } catch (e: Exception) {
                _foodDetail.value = Result.Error(e.message.toString())
                Log.e(TAG, "getFoodDetail: ${e.message.toString()}")
            }
        }
    }

    fun setFavoriteRecipe(index: Int, imageUrl: String?, recipeName: String?) {
        val recipeFavoriteEntity =
            RecipeFavoriteEntity(index = index, imageUrl = imageUrl, recipeName = recipeName)
        foodDetailRepository.setFavoriteRecipe(recipeFavoriteEntity)
    }

    fun removeFavoriteRecipe(recipeFavoriteEntity: RecipeFavoriteEntity) {
        foodDetailRepository.removeFavoriteRecipe(recipeFavoriteEntity)
    }

    fun isFavoriteRecipe(index: Int): LiveData<Boolean> {
        val isFavoriteRecipe = MutableLiveData<Boolean>()
        val favoriteRecipe = foodDetailRepository.getFavoriteRecipe(index)

        favoriteRecipe.observeForever {
            val isFavorite = it != null
            isFavoriteRecipe.value = isFavorite
        }
        return isFavoriteRecipe
    }

    companion object {
        private const val TAG = "DetailRecipeViewModel"
    }
}
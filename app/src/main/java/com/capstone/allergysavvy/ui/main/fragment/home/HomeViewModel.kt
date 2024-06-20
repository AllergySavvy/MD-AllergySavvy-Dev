package com.capstone.allergysavvy.ui.main.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.repository.FoodRecipeRandomRepository
import com.capstone.allergysavvy.data.repository.RandomIngredientRepository
import com.capstone.allergysavvy.data.response.DataItemFoodRandom
import com.capstone.allergysavvy.data.response.DataItemIngredientRandom
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userPreference: UserPreference,
    private val foodRecipeRandomRepository: FoodRecipeRandomRepository,
    private val ingredientRandomRepository: RandomIngredientRepository
) : ViewModel() {

    private val _foodRecipeRandom = MutableLiveData<Result<List<DataItemFoodRandom?>>>()
    val foodRecipeRandom: LiveData<Result<List<DataItemFoodRandom?>>>
        get() = _foodRecipeRandom

    private val _ingredientRandom = MutableLiveData<Result<List<DataItemIngredientRandom?>>>()
    val ingredientRandom: LiveData<Result<List<DataItemIngredientRandom?>>>
        get() = _ingredientRandom

    private val _userName = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _userName

    init {
        getUserName()
    }

    private fun getUserName() {
        viewModelScope.launch {
            _userName.value = userPreference.getUserName()
        }
    }

    fun findFoodRandom() {
        viewModelScope.launch {
            try {
                val resultFoodRandom = foodRecipeRandomRepository.getRandomFoodRecipes()
                _foodRecipeRandom.value = resultFoodRandom
            } catch (e: Exception) {
                _foodRecipeRandom.value = Result.Error(e.message.toString())
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }

    fun findIngredientRandom() {
        viewModelScope.launch {
            try {
                val resultIngredientRandom = ingredientRandomRepository.getRandomIngredient()
                _ingredientRandom.value = resultIngredientRandom
            } catch (e: Exception) {
                _ingredientRandom.value = Result.Error(e.message.toString())
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }


    companion object {
        const val TAG = "HomeViewModel"
    }
}
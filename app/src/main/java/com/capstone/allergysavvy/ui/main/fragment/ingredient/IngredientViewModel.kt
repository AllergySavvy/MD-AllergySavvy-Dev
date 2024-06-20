package com.capstone.allergysavvy.ui.main.fragment.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.allergysavvy.data.repository.IngredientRepository
import com.capstone.allergysavvy.data.response.DataItemIngredient

class IngredientViewModel(
    ingredientRepository: IngredientRepository
) : ViewModel() {

    val ingredient: LiveData<PagingData<DataItemIngredient>> =
        ingredientRepository.getIngredient().cachedIn(viewModelScope)

}
package com.capstone.allergysavvy.ui.main.fragment.ingredient

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.capstone.allergysavvy.data.repository.IngredientRepository
import com.capstone.allergysavvy.data.response.DataItemIngredient
import kotlinx.coroutines.flow.*

class IngredientViewModel(
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    private val query = MutableLiveData("")

    val ingredient: LiveData<PagingData<DataItemIngredient>> =
        query.switchMap { queryString ->
            ingredientRepository.getIngredient()
                .asFlow()
                .map { pagingData ->
                    pagingData.filter { ingredient ->
                        ingredient.ingredient?.contains(queryString, ignoreCase = true) ?: false
                    }
                }
                .cachedIn(viewModelScope)
                .asLiveData()
        }

    fun setQuery(query: String) {
        this.query.value = query
    }
}

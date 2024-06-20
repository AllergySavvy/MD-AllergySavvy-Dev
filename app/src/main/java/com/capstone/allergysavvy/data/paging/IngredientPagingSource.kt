package com.capstone.allergysavvy.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.allergysavvy.data.response.DataItemIngredient
import com.capstone.allergysavvy.data.retrofit.ApiService

class IngredientPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, DataItemIngredient>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItemIngredient>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItemIngredient> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getIngredient()
            val ingredient = response.data?.filterNotNull() ?: emptyList()

            LoadResult.Page(
                data = ingredient,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (ingredient.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
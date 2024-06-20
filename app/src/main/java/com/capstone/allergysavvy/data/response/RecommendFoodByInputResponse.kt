package com.capstone.allergysavvy.data.response

data class RecommendFoodByInputResponse(
    val data: List<DataItemFoodRecommend?>? = null,
    val message: String? = null,
    val status: String? = null
)

data class DataItemFoodRecommend(
    val instructions: List<String?>? = null,
    val ingredientsQuantity: List<String?>? = null,
    val imageUrl: String? = null,
    val cookTimeInMins: String? = null,
    val recipeName: String? = null,
    val cuisine: String? = null
)


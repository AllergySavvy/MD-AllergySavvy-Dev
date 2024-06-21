package com.capstone.allergysavvy.data.response

import com.google.gson.annotations.SerializedName


data class RecommendFoodByInputResponse(

    @field:SerializedName("data")
    val data: List<DataItemFoodRecommendationDetail?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DataItemFoodRecommendationDetail(

    @field:SerializedName("instructions")
    val instructions: List<String?>? = null,

    @field:SerializedName("ingredients_quantity")
    val ingredientsQuantity: List<String?>? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("index")
    val index: Int? = null,

    @field:SerializedName("cook_time_in_mins")
    val cookTimeInMins: String? = null,

    @field:SerializedName("recipe_name")
    val recipeName: String? = null,

    @field:SerializedName("cuisine")
    val cuisine: String? = null
)


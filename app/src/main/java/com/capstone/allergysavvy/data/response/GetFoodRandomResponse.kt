package com.capstone.allergysavvy.data.response

import com.google.gson.annotations.SerializedName

data class GetFoodRandomResponse(

    @field:SerializedName("data")
    val data: List<DataItemFoodRandom?>? = null,

    @field:SerializedName("mstatus")
    val mstatus: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class DataItemFoodRandom(

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("cook_time_in_mins")
    val cookTimeInMins: String? = null,

    @field:SerializedName("index")
    val index: Int? = null,

    @field:SerializedName("recipe_name")
    val recipeName: String? = null,

    @field:SerializedName("cuisine")
    val cuisine: String? = null
)

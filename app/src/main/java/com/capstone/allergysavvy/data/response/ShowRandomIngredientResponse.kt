package com.capstone.allergysavvy.data.response

import com.google.gson.annotations.SerializedName

data class ShowRandomIngredientResponse(

    @field:SerializedName("data")
    val data: List<DataItemIngredientRandom?>? = null,

    @field:SerializedName("mstatus")
    val mstatus: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class DataItemIngredientRandom(

    @field:SerializedName("ingredient")
    val ingredient: String? = null,

    @field:SerializedName("index")
    val index: Int? = null,

    @field:SerializedName("category")
    val category: String? = null
)

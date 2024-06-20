package com.capstone.allergysavvy.data.response

import com.google.gson.annotations.SerializedName

data class ShowAllIngredientResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("ingredient")
	val ingredient: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

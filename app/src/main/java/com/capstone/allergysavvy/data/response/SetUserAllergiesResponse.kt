package com.capstone.allergysavvy.data.response

import com.google.gson.annotations.SerializedName

data class SetUserAllergiesResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

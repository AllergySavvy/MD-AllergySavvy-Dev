package com.capstone.allergysavvy.data.response

import com.google.gson.annotations.SerializedName

data class UserDataResponse(

    @field:SerializedName("data")
    val data: DataUser? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DataUser(

    @field:SerializedName("bio")
    val bio: Any? = null,

    @field:SerializedName("user_allergies")
    val userAllergies: Any? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)

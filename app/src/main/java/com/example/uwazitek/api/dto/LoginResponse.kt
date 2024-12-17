package com.example.uwazitek.api.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken : String,
    @SerializedName("refresh_token")val refreshToken : String,
    val user: User
)
data class User(
    val email: String,
    @SerializedName("first_name")val firstName : String,
    val id: Int,
    @SerializedName("last_name")val lastName : String,
    @SerializedName("user_name")val userName : String,
    val organisation: Organisation
)
data class Organisation(
    val id: Int,
    val name: String,
    val role: String,
    val type: String
)

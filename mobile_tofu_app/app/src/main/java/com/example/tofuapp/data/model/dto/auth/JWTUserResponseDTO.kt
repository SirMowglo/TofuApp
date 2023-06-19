package com.example.tofuapp.data.model.dto.auth

import com.google.gson.annotations.SerializedName

data class JWTUserResponseDTO(
    @SerializedName("username")
    val username: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)
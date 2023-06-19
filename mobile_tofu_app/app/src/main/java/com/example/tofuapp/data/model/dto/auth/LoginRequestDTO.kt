package com.example.tofuapp.data.model.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestDTO(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
)
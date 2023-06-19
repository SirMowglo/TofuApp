package com.example.tofuapp.data.model.dto.auth

data class RefreshTokenResponseDTO(
    val refreshToken: String,
    val token: String
)
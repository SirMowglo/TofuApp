package com.example.tofuapp.data.model.dto.auth

data class RegisterUserRequestDTO(
    val email: String,
    val fullname: String,
    val password: String,
    val username: String,
    val verifyEmail: String,
    val verifyPassword: String
)
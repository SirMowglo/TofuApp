package com.example.tofuapp.data.service

import com.example.tofuapp.data.model.dto.auth.JWTUserResponseDTO
import com.example.tofuapp.data.model.dto.auth.LoginRequestDTO
import com.example.tofuapp.data.model.dto.auth.RefreshTokenResponseDTO
import com.example.tofuapp.data.model.dto.auth.RegisterUserRequestDTO
import com.example.tofuapp.data.model.dto.user.UserResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequestDTO
    ): Response<JWTUserResponseDTO>

    @POST("refreshToken")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): Response<RefreshTokenResponseDTO>

    @POST("auth/register")
    suspend fun register(
       @Body registerUserRequest: RegisterUserRequestDTO
    ): Response<UserResponseDTO>
}
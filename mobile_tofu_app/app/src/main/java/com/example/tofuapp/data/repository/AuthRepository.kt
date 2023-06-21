package com.example.tofuapp.data.repository

import com.example.tofuapp.data.model.dto.auth.LoginRequestDTO
import com.example.tofuapp.data.model.dto.auth.RegisterUserRequestDTO
import com.example.tofuapp.data.service.AuthService
import com.example.tofuapp.util.apiRequestFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
) {
    fun login(loginRequest: LoginRequestDTO) = apiRequestFlow {
        authService.login(loginRequest)
    }

    fun register(registerUserRequest: RegisterUserRequestDTO) = apiRequestFlow {
        authService.register(registerUserRequest)
    }
}
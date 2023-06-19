package com.example.tofuapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tofuapp.data.model.dto.auth.JWTUserResponseDTO
import com.example.tofuapp.data.model.dto.auth.LoginRequestDTO
import com.example.tofuapp.data.repository.AuthRepository
import com.example.tofuapp.util.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel() {
    private val _loginResponse = MutableLiveData<ApiResponse<JWTUserResponseDTO>>()
    val loginResponse: LiveData<ApiResponse<JWTUserResponseDTO>> = _loginResponse

    fun login(loginRequest: LoginRequestDTO, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _loginResponse,
        coroutineErrorHandler
    ){
        authRepository.login(loginRequest)
    }
}
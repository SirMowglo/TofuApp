package com.example.tofuapp.data.service

import com.example.tofuapp.data.model.dto.user.UserDetailsResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("user/me")
    suspend fun getCurrentUser(): Response<UserDetailsResponseDTO>
}
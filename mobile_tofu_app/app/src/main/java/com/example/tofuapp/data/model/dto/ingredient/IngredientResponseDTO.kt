package com.example.tofuapp.data.model.dto.ingredient

import com.example.tofuapp.data.model.dto.user.UserResponseDTO

data class IngredientResponseDTO(
    val author: UserResponseDTO,
    val description: String,
    val id: String,
    val img: String,
    val name: String
)
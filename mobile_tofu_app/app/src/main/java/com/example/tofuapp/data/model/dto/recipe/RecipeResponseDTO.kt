package com.example.tofuapp.data.model.dto.recipe

import com.example.tofuapp.data.model.dto.category.CategoryResponseDTO
import com.example.tofuapp.data.model.dto.user.UserResponseDTO

data class RecipeResponseDTO(
    val author: UserResponseDTO,
    val categories: List<CategoryResponseDTO>,
    val createdAt: String,
    val id: String,
    val img: String,
    val name: String,
    val nlikes: Int,
    val prepTime: Int,
    val type: String
)
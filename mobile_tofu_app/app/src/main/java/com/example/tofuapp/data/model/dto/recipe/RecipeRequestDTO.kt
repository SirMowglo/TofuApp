package com.example.tofuapp.data.model.dto.recipe

data class RecipeRequestDTO(
    val description: String,
    val name: String,
    val prepTime: Int,
    val type: String
)
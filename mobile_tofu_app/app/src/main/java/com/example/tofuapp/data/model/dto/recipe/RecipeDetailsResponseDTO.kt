package com.example.tofuapp.data.model.dto.recipe

import com.example.tofuapp.data.model.dto.ingredient.IngredientResponseDTO
import com.example.tofuapp.data.model.dto.step.StepResponseDTO
import com.example.tofuapp.data.model.dto.user.UserResponseDTO

data class RecipeDetailsResponseDTO(
    val author: UserResponseDTO,
    val categories: List<Any>,
    val createdAt: String,
    val description: String,
    val id: String,
    val img: String,
    val ingredients: List<IngredientResponseDTO>,
    val name: String,
    val nlikes: Int,
    val prepTime: Int,
    val steps: List<StepResponseDTO>,
    val type: String
)
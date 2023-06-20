package com.example.tofuapp.data.repository

import com.example.tofuapp.data.service.RecipeService
import com.example.tofuapp.util.apiRequestFlow
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeService: RecipeService,
) {
    fun getRecipesByAuthor(username: String) = apiRequestFlow {
        recipeService.getRecipesByAuthor(username)
    }
    fun getRecipes() = apiRequestFlow {
        recipeService.getRecipes()
    }
}
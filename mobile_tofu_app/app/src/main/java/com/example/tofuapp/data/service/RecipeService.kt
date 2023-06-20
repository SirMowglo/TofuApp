package com.example.tofuapp.data.service

import com.example.tofuapp.data.model.dto.PageDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("recipe/author/{username}")
    suspend fun getRecipesByAuthor(@Path("username") username: String): Response<PageDTO<RecipeResponseDTO>>
    @GET("recipe")
    suspend fun getRecipes(): Response<PageDTO<RecipeResponseDTO>>
}
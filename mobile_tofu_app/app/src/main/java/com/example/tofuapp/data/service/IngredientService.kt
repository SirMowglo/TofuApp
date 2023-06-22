package com.example.tofuapp.data.service

import com.example.tofuapp.data.model.dto.PageDTO
import com.example.tofuapp.data.model.dto.ingredient.IngredientResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IngredientService {
    @GET("ingredient/recipe/{idRecipe}")
    suspend fun getIngredientByRecipe(@Path("idRecipe") idRecipe: String): Response<PageDTO<IngredientResponseDTO>>
}
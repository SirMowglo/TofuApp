package com.example.tofuapp.data.service

import com.example.tofuapp.data.model.dto.PageDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeRequestDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @GET("recipe/author/{username}")
    suspend fun getRecipesByAuthor(@Path("username") username: String): Response<PageDTO<RecipeResponseDTO>>
    @GET("recipe")
    suspend fun getRecipes(): Response<PageDTO<RecipeResponseDTO>>

    @POST("recipe")
    suspend fun addRecipe(@Body recipeRequestDTO: RecipeRequestDTO): Response<RecipeResponseDTO>
    @DELETE("recipe/{recipeId}")
    suspend fun deleteRecipe(@Path("recipeId") recipeId: String): Response<ResponseBody>
}
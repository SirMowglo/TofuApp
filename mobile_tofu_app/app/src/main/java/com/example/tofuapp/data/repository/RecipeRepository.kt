package com.example.tofuapp.data.repository

import com.example.tofuapp.data.model.dto.error.ErrorResponseDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeRequestDTO
import com.example.tofuapp.data.service.RecipeService
import com.example.tofuapp.util.ApiResponse
import com.example.tofuapp.util.apiRequestFlow
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
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

    fun addRecipe(recipeRequest: RecipeRequestDTO) = apiRequestFlow {
        recipeService.addRecipe(recipeRequest)
    }

    suspend fun deleteRecipe(recipeId: String): ApiResponse<Boolean> {
        val response = recipeService.deleteRecipe(recipeId)
        if(response.isSuccessful){
            return ApiResponse.Success(true)
        }else{
            response.errorBody()?.let {error ->
                error.close()
                val parsedError: ErrorResponseDTO = Gson().fromJson(error.charStream(), ErrorResponseDTO::class.java)
                return ApiResponse.Failure(parsedError.message, parsedError.status)
            }
        }
        return ApiResponse.Failure("Something Happened", "UNAUTHORIZED")
    }

}
package com.example.tofuapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tofuapp.data.model.dto.recipe.RecipeRequestDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import com.example.tofuapp.data.model.dto.user.UserResponseDTO
import com.example.tofuapp.data.repository.RecipeRepository
import com.example.tofuapp.util.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): BaseViewModel() {
    private val _recipeResponse = MutableLiveData<ApiResponse<RecipeResponseDTO>>()
    val recipeResponse: LiveData<ApiResponse<RecipeResponseDTO>> = _recipeResponse


    fun addRecipe(recipeRequest: RecipeRequestDTO, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _recipeResponse,
        coroutineErrorHandler
    ){
        recipeRepository.addRecipe(recipeRequest)
    }
}
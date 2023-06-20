package com.example.tofuapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tofuapp.data.model.dto.PageDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import com.example.tofuapp.data.repository.RecipeRepository
import com.example.tofuapp.util.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): BaseViewModel() {
    private val _recipeResponse = MutableLiveData<ApiResponse<PageDTO<RecipeResponseDTO>>>()
    val recipeResponse: LiveData<ApiResponse<PageDTO<RecipeResponseDTO>>> = _recipeResponse

    private val _recipeList = MutableLiveData<List<RecipeResponseDTO>>()
    val recipeList: LiveData<List<RecipeResponseDTO>> = _recipeList

    fun getRecipes(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _recipeResponse,
        coroutineErrorHandler
    ){
        recipeRepository.getRecipes()
    }

    fun saveRecipes(newList: List<RecipeResponseDTO>){
        _recipeList.postValue(newList)
    }
}
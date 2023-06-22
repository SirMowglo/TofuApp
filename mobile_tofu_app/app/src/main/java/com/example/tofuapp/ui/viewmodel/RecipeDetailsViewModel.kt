package com.example.tofuapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tofuapp.data.model.dto.PageDTO
import com.example.tofuapp.data.model.dto.ingredient.IngredientResponseDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeDetailsResponseDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import com.example.tofuapp.data.model.dto.step.StepResponseDTO
import com.example.tofuapp.data.model.dto.user.UserDetailsResponseDTO
import com.example.tofuapp.data.repository.IngredientRepository
import com.example.tofuapp.data.repository.RecipeRepository
import com.example.tofuapp.data.repository.StepRepository
import com.example.tofuapp.util.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val stepRepository: StepRepository,
    private val ingredientRepository: IngredientRepository,
): BaseViewModel() {

    private val _recipeResponse = MutableLiveData<ApiResponse<RecipeDetailsResponseDTO>>()
    val recipeResponse: LiveData<ApiResponse<RecipeDetailsResponseDTO>> = _recipeResponse
    private val _recipe = MutableLiveData<RecipeDetailsResponseDTO>()
    val recipe: LiveData<RecipeDetailsResponseDTO> = _recipe

    private val _stepResponse = MutableLiveData<ApiResponse<List<StepResponseDTO>>>()
    val stepResponse: LiveData<ApiResponse<List<StepResponseDTO>>> = _stepResponse
    private val _stepList = MutableLiveData<List<StepResponseDTO>>()
    val stepList: LiveData<List<StepResponseDTO>> = _stepList

    private val _ingredientResponse = MutableLiveData<ApiResponse<PageDTO<IngredientResponseDTO>>>()
    val ingredientResponse: LiveData<ApiResponse<PageDTO<IngredientResponseDTO>>> = _ingredientResponse
    private val _ingredientList = MutableLiveData<List<IngredientResponseDTO>>()
    val ingredientList: LiveData<List<IngredientResponseDTO>> = _ingredientList

    //RECIPE
    fun getRecipeDetails(recipeId:String ,coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _recipeResponse,
        coroutineErrorHandler
    ){
        recipeRepository.getRecipeById(recipeId)
    }

    fun saveRecipe(newRecipe: RecipeDetailsResponseDTO){
        _recipe.postValue(newRecipe)
    }

    //STEPS
    fun getStepsByRecipe(recipeId: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _stepResponse,
        coroutineErrorHandler
    ){
        stepRepository.getStepByRecipe(recipeId)
    }

    fun saveSteps(newStepList: List<StepResponseDTO>){
        _stepList.postValue(newStepList)
    }

    //INGREDIENTS
    fun getIngredientsByRecipe(recipeId: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _ingredientResponse,
        coroutineErrorHandler
    ){
        ingredientRepository.getIngredientByRecipe(recipeId)
    }

    fun saveIngredients(newIngredientList: List<IngredientResponseDTO>){
        _ingredientList.postValue(newIngredientList)
    }
}
package com.example.tofuapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tofuapp.data.model.dto.PageDTO
import com.example.tofuapp.data.model.dto.recipe.RecipeResponseDTO
import com.example.tofuapp.data.model.dto.user.UserDetailsResponseDTO
import com.example.tofuapp.data.repository.RecipeRepository
import com.example.tofuapp.data.repository.UserRepository
import com.example.tofuapp.util.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class CurrentUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository
):BaseViewModel() {
    private val _userResponse = MutableLiveData<ApiResponse<UserDetailsResponseDTO>>()
    val userResponse: LiveData<ApiResponse<UserDetailsResponseDTO>> = _userResponse

    private val _user = MutableLiveData<UserDetailsResponseDTO>()
    val user: LiveData<UserDetailsResponseDTO> = _user

    private val _recipeResponse = MutableLiveData<ApiResponse<PageDTO<RecipeResponseDTO>>>()
    val recipeResponse: LiveData<ApiResponse<PageDTO<RecipeResponseDTO>>> = _recipeResponse

    private val _deleteRecipeResponse = MutableLiveData<ApiResponse<Boolean>>()
    val deleteRecipeResponse: LiveData<ApiResponse<Boolean>> = _deleteRecipeResponse

    private val _recipeList = MutableLiveData<List<RecipeResponseDTO>>()
    val recipeList: LiveData<List<RecipeResponseDTO>> = _recipeList


    fun getCurrentUser(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _userResponse,
        coroutineErrorHandler
    ){
        userRepository.getCurrentUser()
    }

    fun getRecipesUser(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _recipeResponse,
        coroutineErrorHandler
    ){
        recipeRepository.getRecipesByAuthor(_user.value?.username ?: "")
    }

    fun deleteRecipe(recipeId:String){
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipeId).let { response ->
                _deleteRecipeResponse.postValue(response)
            }
        }
    }

    fun resetDeleteRecipe(){
        _deleteRecipeResponse.value = ApiResponse.Loading
    }
    fun saveUser(newUser: UserDetailsResponseDTO){
        _user.postValue(newUser)
    }

    fun saveRecipes(newList: List<RecipeResponseDTO>){
        _recipeList.postValue(newList)
    }
}
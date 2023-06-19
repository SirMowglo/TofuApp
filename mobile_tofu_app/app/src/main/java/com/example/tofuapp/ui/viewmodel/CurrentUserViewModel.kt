package com.example.tofuapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tofuapp.data.model.dto.user.UserDetailsResponseDTO
import com.example.tofuapp.data.repository.UserRepository
import com.example.tofuapp.util.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
):BaseViewModel() {
    private val _userResponse = MutableLiveData<ApiResponse<UserDetailsResponseDTO>>()
    val userResponse: LiveData<ApiResponse<UserDetailsResponseDTO>> = _userResponse

    fun getCurrentUser(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _userResponse,
        coroutineErrorHandler
    ){
        userRepository.getCurrentUser()
    }
}
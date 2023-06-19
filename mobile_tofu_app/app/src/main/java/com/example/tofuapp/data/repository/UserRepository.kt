package com.example.tofuapp.data.repository

import com.example.tofuapp.data.service.UserService
import com.example.tofuapp.util.apiRequestFlow
import javax.inject.Inject

class UserRepository @Inject constructor(
   private val userService: UserService,
) {
    fun getCurrentUser() = apiRequestFlow {
        userService.getCurrentUser()
    }
}
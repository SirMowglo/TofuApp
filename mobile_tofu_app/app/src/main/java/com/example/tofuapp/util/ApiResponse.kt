package com.example.tofuapp.util

sealed class ApiResponse<out T> {
    object  Loading: ApiResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ):ApiResponse<T>()

    data class Failure(
        val errorMessage: String,
        val status: String,
    ):ApiResponse<Nothing>()
}
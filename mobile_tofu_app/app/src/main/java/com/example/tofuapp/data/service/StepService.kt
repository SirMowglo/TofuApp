package com.example.tofuapp.data.service

import com.example.tofuapp.data.model.dto.step.StepResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StepService {
    @GET("step/recipe/{idRecipe}")
    suspend fun getStepsByRecipe(@Path("idRecipe") idRecipe: String): Response<List<StepResponseDTO>>
}
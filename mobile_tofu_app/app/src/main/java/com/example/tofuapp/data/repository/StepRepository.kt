package com.example.tofuapp.data.repository

import com.example.tofuapp.data.service.StepService
import com.example.tofuapp.util.apiRequestFlow
import javax.inject.Inject

class StepRepository @Inject constructor(
    private val stepService: StepService
) {
    fun getStepByRecipe(recipeId: String) = apiRequestFlow {
        stepService.getStepsByRecipe(recipeId)
    }
}
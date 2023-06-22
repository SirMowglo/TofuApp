package com.example.tofuapp.data.repository

import com.example.tofuapp.data.service.IngredientService
import com.example.tofuapp.util.apiRequestFlow
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val ingredientService: IngredientService,
) {
    fun getIngredientByRecipe(idRecipe:String) = apiRequestFlow {
        ingredientService.getIngredientByRecipe(idRecipe)
    }
}
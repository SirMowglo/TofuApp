package com.trianaSalesianos.tofuApp.model.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientRequest {
    private double amount;
    private String unit;
}

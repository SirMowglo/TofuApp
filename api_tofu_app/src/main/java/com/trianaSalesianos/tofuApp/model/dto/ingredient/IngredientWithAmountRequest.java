package com.trianaSalesianos.tofuApp.model.dto.ingredient;

import com.fasterxml.jackson.annotation.JsonView;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientWithAmountRequest {

    private String name;
    private String img;
    private double amount;
    private String unit;
}

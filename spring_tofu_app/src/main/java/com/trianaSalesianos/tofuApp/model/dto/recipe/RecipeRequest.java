package com.trianaSalesianos.tofuApp.model.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeRequest {
    @Size(max = 64)
    private String name;
    @Size(max = 254)
    private String description;
    @Size(max = 24)
    private String category;

    private String steps;
    @Max(1000)
    @Min(0)
    private int prepTime;
}

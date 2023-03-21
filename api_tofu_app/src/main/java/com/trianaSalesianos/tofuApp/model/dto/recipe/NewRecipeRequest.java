package com.trianaSalesianos.tofuApp.model.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewRecipeRequest {
    @NotEmpty(message = "")
    @Size(max = 64, message = "{recipeRequest.name.sizemax}")
    private String name;
    @Size(max = 254, message = "{recipeRequest.description.sizemax}")
    private String description;
    @Size(max = 24, message = "{recipeRequest.category.sizemax}")
    private String category;
    @NotEmpty(message = "")
    private String steps;
    private String img;
    @Max(value = 1000, message = "{recipeRequest.preptime.max}")
    @Min(value = 0, message = "{recipeRequest.preptime.min}")
    private Integer prepTime;
}

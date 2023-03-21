package com.trianaSalesianos.tofuApp.model.dto.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.RecipeIngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDetailsResponse {
    private String name, description, category, img, author, steps;
    private Integer prepTime, favorites;
    private List<RecipeIngredientResponse> ingredients;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    public static RecipeDetailsResponse fromRecipe(Recipe recipe){
        return RecipeDetailsResponse.builder()
                .name(recipe.getName())
                .description(recipe.getDescription())
                .category(recipe.getCategory())
                .img(recipe.getImg())
                .author(recipe.getAuthor().getFullname())
                .prepTime(recipe.getPrepTime())
                .steps(recipe.getSteps())
                .favorites(recipe.getFavoritedBy().size())
                .ingredients(recipe.getRecipeIngredients()
                        .stream()
                        .map(RecipeIngredientResponse::fromRecipeIngredient)
                        .collect(Collectors.toList()))
                .createdAt(recipe.getCreatedAt())
                .build();
    }
}

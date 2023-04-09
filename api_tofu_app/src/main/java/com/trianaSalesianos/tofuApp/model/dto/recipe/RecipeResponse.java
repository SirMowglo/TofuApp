package com.trianaSalesianos.tofuApp.model.dto.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RecipeResponse {
    private UUID id;
    private String name, description, category, img, author;
    private Integer prepTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    public static RecipeResponse fromRecipe(Recipe recipe){
        return RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .category(recipe.getCategories().stream().findFirst().get().getName())
                .img(recipe.getImg())
                .author(recipe.getAuthor().getFullname())
                .prepTime(recipe.getPrepTime())
                .createdAt(recipe.getCreatedAt())
                .build();
    }
}

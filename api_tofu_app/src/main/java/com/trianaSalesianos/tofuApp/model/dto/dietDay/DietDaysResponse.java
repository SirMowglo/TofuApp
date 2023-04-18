package com.trianaSalesianos.tofuApp.model.dto.dietDay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.DietDays;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DietDaysResponse {

    private UUID id;
    private String user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime day;
    private List<RecipeResponse> listOfRecipes;

    public static DietDaysResponse fromDietDay(DietDays d){
        return DietDaysResponse.builder()
                .id(d.getId())
                .user(d.getUser().getUsername())
                .day(d.getDay())
                .listOfRecipes(d.getRecipes().stream().map(RecipeResponse::fromRecipe).collect(Collectors.toList()))
                .build();
    }
}

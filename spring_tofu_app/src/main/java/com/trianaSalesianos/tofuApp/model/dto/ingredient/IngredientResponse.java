package com.trianaSalesianos.tofuApp.model.dto.ingredient;

import com.fasterxml.jackson.annotation.JsonView;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientResponse {
    @JsonView({IngredientViews.Full.class,IngredientViews.Name.class})
    private String name;
    @JsonView({IngredientViews.Full.class,IngredientViews.Img.class})
    private String img;

    public static IngredientResponse fromIngredient(Ingredient ingredient) {
        return IngredientResponse.builder()
                .name(ingredient.getName())
                .img(ingredient.getImg())
                .build();
    }
}

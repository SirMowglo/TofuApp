package com.trianaSalesianos.tofuApp.model.dto.ingredient;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientRequest {
    @JsonView({IngredientViews.Full.class,IngredientViews.Name.class})
    @NotEmpty(message = "{ingredientRequest.name.notempty}")
    private String name;
    @JsonView({IngredientViews.Full.class,IngredientViews.Img.class})
    private String img;
}

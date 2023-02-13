package com.trianaSalesianos.tofuApp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Ingredient {
    //TODO Modelado de los ingredientes
    @Id
    private UUID id;
    private String name;
    private String img;
    @Builder.Default
    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
}

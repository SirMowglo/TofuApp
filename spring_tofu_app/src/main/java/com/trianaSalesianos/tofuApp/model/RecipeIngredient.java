package com.trianaSalesianos.tofuApp.model;

import lombok.*;

import javax.persistence.Entity;
import java.util.UUID;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class RecipeIngredient {
    UUID id_recipe;
    UUID id_ingredient;
    double amount;
    String unit; //in cm, pieces,
}

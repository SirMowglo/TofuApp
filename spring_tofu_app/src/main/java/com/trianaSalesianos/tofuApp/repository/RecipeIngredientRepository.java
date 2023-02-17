package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.RecipeIngredient;
import com.trianaSalesianos.tofuApp.model.RecipeIngredientPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientPK>, JpaSpecificationExecutor<RecipeIngredient> {
}

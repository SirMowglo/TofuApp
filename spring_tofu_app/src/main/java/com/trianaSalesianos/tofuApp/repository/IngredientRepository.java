package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
}

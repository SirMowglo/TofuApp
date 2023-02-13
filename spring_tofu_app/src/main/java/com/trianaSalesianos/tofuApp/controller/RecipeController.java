package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    final private RecipeService recipeService;

    @PostMapping("/")
    public ResponseEntity<Recipe> createRecipe(@AuthenticationPrincipal User loggedUser){
        return null;
    }
}

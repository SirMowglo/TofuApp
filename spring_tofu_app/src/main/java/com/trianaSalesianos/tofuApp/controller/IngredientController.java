package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IngredientController {
    final private IngredientService ingredientService;
}

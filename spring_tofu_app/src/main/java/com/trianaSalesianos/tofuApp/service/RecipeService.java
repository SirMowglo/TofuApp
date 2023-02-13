package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.repository.RecipeRepository;
import com.trianaSalesianos.tofuApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
}

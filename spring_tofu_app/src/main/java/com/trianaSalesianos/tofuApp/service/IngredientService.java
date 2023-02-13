package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {
    final private IngredientRepository ingredientRepository;
}

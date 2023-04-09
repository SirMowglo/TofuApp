package com.trianaSalesianos.tofuApp.service;


import com.trianaSalesianos.tofuApp.exception.IngredientNotFoundException;
import com.trianaSalesianos.tofuApp.exception.RecipeAuthorNotValidException;
import com.trianaSalesianos.tofuApp.exception.RecipeNotFoundException;
import com.trianaSalesianos.tofuApp.model.*;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientWithAmountRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.RecipeIngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeDetailsResponse;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeRequest;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserLikesResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserResponse;
import com.trianaSalesianos.tofuApp.repository.IngredientRepository;
import com.trianaSalesianos.tofuApp.repository.RecipeIngredientRepository;
import com.trianaSalesianos.tofuApp.repository.RecipeRepository;
import com.trianaSalesianos.tofuApp.repository.UserRepository;
import com.trianaSalesianos.tofuApp.search.spec.GenericSpecificationBuilder;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteria;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteriaExtractor;
import com.trianaSalesianos.tofuApp.service.files.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final StorageService storageService;
    private final UserRepository userRepository;

    public PageDto<RecipeResponse> search(List<SearchCriteria> params, Pageable pageable) {
        GenericSpecificationBuilder<Recipe> recipeSpecificationBuilder = new GenericSpecificationBuilder<>(params, Recipe.class);

        Specification<Recipe> spec = recipeSpecificationBuilder.build();
        Page<RecipeResponse> recipeResponsePage = recipeRepository.findAll(spec, pageable).map(RecipeResponse::fromRecipe);

        return new PageDto<>(recipeResponsePage);
    }

    public PageDto<RecipeResponse> getAllBySearch(String search, Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<RecipeResponse> res = search(params, pageable);

        if (res.getContent().isEmpty()) throw new RecipeNotFoundException();

        return res;
    }

    public Recipe findById(UUID id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Transactional
    public RecipeResponse changeImg(MultipartFile file, UUID id, User user) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if(!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        String filename = storageService.store(file);

        recipe.setImg(filename);

        return RecipeResponse.fromRecipe(recipeRepository.save(recipe));
    }

    public RecipeResponse update(RecipeRequest recipeRequest, UUID id, User user) {
        Recipe rec = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (!recipeRequest.getName().isEmpty())
            rec.setName(recipeRequest.getName());

        if (!recipeRequest.getDescription().isEmpty())
            rec.setDescription(recipeRequest.getDescription());

        if (!recipeRequest.getPrepTime().equals(0)) {
            rec.setPrepTime(recipeRequest.getPrepTime());
        }


        return RecipeResponse.fromRecipe(recipeRepository.save(rec));
    }

    public RecipeDetailsResponse addIngredient(UUID id_recipe,
                                               UUID id_ingredient,
                                               RecipeIngredientRequest recipeIngredientRequest,
                                               User user) {

        Recipe recipe = recipeRepository.findById(id_recipe)
                .orElseThrow(() -> new RecipeNotFoundException());

        Ingredient ingredient = ingredientRepository.findById(id_ingredient)
                .orElseThrow(() -> new IngredientNotFoundException());


        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();
        RecipeIngredient ri;

        if (!recipeIngredientRepository.existsById(new RecipeIngredientPK(id_recipe, id_ingredient))) {
            ri = RecipeIngredient.builder()
                    .unit(recipeIngredientRequest.getUnit())
                    .amount(recipeIngredientRequest.getAmount())
                    .recipe(recipe)
                    .ingredient(ingredient)
                    .build();

            recipe.getRecipeIngredients().add(ri);
            ingredient.getRecipeIngredients().add(ri);
        } else {
            ri = recipeIngredientRepository.findById(new RecipeIngredientPK(id_recipe, id_ingredient)).get();

            double amount = ri.getAmount();

            ri.setAmount(amount + recipeIngredientRequest.getAmount());
        }

        recipeIngredientRepository.save(ri);
        recipeRepository.save(recipe);
        ingredientRepository.save(ingredient);

        return RecipeDetailsResponse.fromRecipe(recipe);
    }

    public UserLikesResponse likeRecipe(UUID id, User user) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (recipeRepository.isFavoritedByUser(user.getId(), id)) {
            user.getFavorites().removeIf(f -> f.getId().equals(recipe.getId()));
            recipe.getFavoritedBy().removeIf(u -> u.getId().equals(user.getId()));
        } else {
            user.getFavorites().add(recipe);
            recipe.getFavoritedBy().add(user);
        }

        userRepository.save(user);
        recipeRepository.save(recipe);

        return UserLikesResponse.fromUser(user);
    }

    public RecipeDetailsResponse updateAmount(UUID id_recipe, UUID id_ingredient, RecipeIngredientRequest recipeIngredientRequest, User user) {
        Recipe recipe = recipeRepository.findById(id_recipe)
                .orElseThrow(() -> new RecipeNotFoundException());
        Ingredient ingredient = ingredientRepository.findById(id_ingredient)
                .orElseThrow(() -> new IngredientNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        if (!recipe.getRecipeIngredients()
                .stream()
                .anyMatch(i -> i.getIngredient().getId().equals(id_ingredient)))
            throw new IngredientNotFoundException();

        recipeRepository.updateAmount(
                recipeIngredientRequest.getUnit(),
                recipeIngredientRequest.getAmount(),
                ingredient, recipe);

        recipeRepository.save(recipe);
        ingredientRepository.save(ingredient);

        return RecipeDetailsResponse.fromRecipe(recipe);
    }

    public RecipeDetailsResponse createIngredientInRecipe(UUID id, IngredientWithAmountRequest ingredient, User user) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        Ingredient ing = Ingredient.builder()
                .name(ingredient.getName())
                .img(ingredient.getImg())
                .build();

        RecipeIngredient ri = RecipeIngredient.builder()
                .ingredient(ing)
                .recipe(recipe)
                .amount(ingredient.getAmount())
                .unit(ingredient.getUnit())
                .build();

        recipeIngredientRepository.save(ri);
        recipeRepository.save(recipe);
        ingredientRepository.save(ing);

        return RecipeDetailsResponse.fromRecipe(recipe);
    }
}

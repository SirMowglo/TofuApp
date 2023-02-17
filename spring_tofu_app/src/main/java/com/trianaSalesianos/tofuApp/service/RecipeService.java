package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.exception.IngredientNotFoundException;
import com.trianaSalesianos.tofuApp.exception.RecipeNotFoundException;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.RecipeIngredient;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
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
    public RecipeResponse changeImg(MultipartFile file, UUID id) {
        String filename = storageService.store(file);
        Recipe rec = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        rec.setImg(filename);

        return RecipeResponse.fromRecipe(recipeRepository.save(rec));
    }

    public RecipeResponse update(RecipeRequest recipeRequest, UUID id) {
        Recipe rec = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (!recipeRequest.getName().isEmpty())
            rec.setName(recipeRequest.getName());

        if (!recipeRequest.getDescription().isEmpty())
            rec.setDescription(recipeRequest.getDescription());

        if (!recipeRequest.getCategory().isEmpty())
            rec.setCategory(recipeRequest.getCategory());

        if (!recipeRequest.getSteps().isEmpty())
            rec.setSteps(recipeRequest.getSteps());

        if (!recipeRequest.getPrepTime().equals(0)) {
            rec.setPrepTime(recipeRequest.getPrepTime());
        }


        return RecipeResponse.fromRecipe(recipeRepository.save(rec));
    }

    public RecipeDetailsResponse addIngredient(UUID id_recipe,
                                               UUID id_ingredient,
                                               RecipeIngredientRequest recipeIngredientRequest) {

        Recipe recipe = recipeRepository.findById(id_recipe)
                .orElseThrow(() -> new RecipeNotFoundException());

        Ingredient ingredient = ingredientRepository.findById(id_ingredient)
                .orElseThrow(() -> new IngredientNotFoundException());


        RecipeIngredient ri = RecipeIngredient.builder()
                .unit(recipeIngredientRequest.getUnit())
                .amount(recipeIngredientRequest.getAmount())
                .recipe(recipe)
                .ingredient(ingredient)
                .build();


        recipe.getRecipeIngredients().add(ri);
        ingredient.getRecipeIngredients().add(ri);


        recipeIngredientRepository.save(ri);
        recipeRepository.save(recipe);
        ingredientRepository.save(ingredient);

        return RecipeDetailsResponse.fromRecipe(recipe);
    }

    public UserLikesResponse likeRecipe(UUID id, User user) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());


        user.getFavorites().add(recipe);
        recipe.getFavoritedBy().add(user);

        userRepository.save(user);
        recipeRepository.save(recipe);


        return UserLikesResponse.fromUser(user);
    }
}

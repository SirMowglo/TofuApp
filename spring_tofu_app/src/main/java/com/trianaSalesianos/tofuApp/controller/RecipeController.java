package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.RecipeIngredient;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientWithAmountRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.RecipeIngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.recipe.NewRecipeRequest;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeDetailsResponse;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeRequest;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserLikesResponse;
import com.trianaSalesianos.tofuApp.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    final private RecipeService recipeService;


    @GetMapping("/")
    public PageDto<RecipeResponse> getAll(@RequestParam(value = "search", defaultValue = "") String search,
                                          @PageableDefault(size = 10, page = 0) Pageable pageable){
        return recipeService.getAllBySearch(search, pageable);
    }

    @GetMapping("/{id}")
    public RecipeDetailsResponse getById(@PathVariable UUID id){
        return RecipeDetailsResponse.fromRecipe(recipeService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<RecipeResponse> create(
            @AuthenticationPrincipal User loggedUser,
            @Valid @RequestBody NewRecipeRequest recipeRequest
    ){
        Recipe created = Recipe.builder()
                .name(recipeRequest.getName())
                .description(recipeRequest.getDescription())
                .author(loggedUser)
                .steps(recipeRequest.getSteps())
                .category(recipeRequest.getCategory())
                .prepTime(recipeRequest.getPrepTime())
                .build();

        if(!recipeRequest.getImg().isEmpty())
            created.setImg(recipeRequest.getImg());

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(RecipeResponse.fromRecipe(recipeService.save(created)));
    }

    @PutMapping("/img/{id}")
    public RecipeResponse changeImg(@RequestPart("file") MultipartFile file,
                                    @PathVariable UUID id,
                                    @AuthenticationPrincipal User user
    ){
        return recipeService.changeImg(file,id, user);
    }

    @PutMapping("/{id}")
    public RecipeResponse update(@PathVariable UUID id,
                                 @Valid @RequestBody RecipeRequest recipeRequest,
                                 @AuthenticationPrincipal User user){
        return recipeService.update(recipeRequest, id, user);
    }

    @PostMapping("/{id_recipe}/ingredient/{id_ingredient}")
    public RecipeDetailsResponse addIngredientToRecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @PathVariable UUID id_ingredient,
            @RequestBody RecipeIngredientRequest recipeIngredientRequest
            ){
        return recipeService.addIngredient(id_recipe,id_ingredient,recipeIngredientRequest,user);
    }
    @PostMapping("/{id_recipe}/ingredient/")
    public RecipeDetailsResponse addIngredientToRecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @RequestBody IngredientWithAmountRequest ingredient
    ){
        return recipeService.createIngredientInRecipe(id_recipe,ingredient, user);
    }
    @PostMapping("/{id}/like")
    public UserLikesResponse likeRecipe(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user){

        return recipeService.likeRecipe(id, user);
    }

    @PutMapping("/{id_recipe}/ingredient/{id_ingredient}/changeamount")
    public RecipeDetailsResponse updateAmount(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @PathVariable UUID id_ingredient,
            @RequestBody RecipeIngredientRequest recipeIngredientRequest
    ){
        return recipeService.updateAmount(id_recipe, id_ingredient, recipeIngredientRequest, user);
    }

}

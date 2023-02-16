package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.recipe.NewRecipeRequest;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeDetailsResponse;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeRequest;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import com.trianaSalesianos.tofuApp.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        recipeService.save(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(RecipeResponse.fromRecipe(created));
    }

    @PutMapping("/img/{id}")
    public RecipeResponse changeImg(@RequestPart("file") MultipartFile file,
                                    @PathVariable UUID id){
        return recipeService.changeImg(file,id);
    }

    @PutMapping("/{id}")
    public RecipeResponse update(@PathVariable UUID id,
                                 @Valid @RequestBody RecipeRequest recipeRequest){
        return recipeService.update(recipeRequest, id);
    }
}

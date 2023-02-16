package com.trianaSalesianos.tofuApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientViews;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {
    final private IngredientService ingredientService;

    @JsonView(IngredientViews.Full.class)
    @GetMapping("/")
    public PageDto<IngredientResponse> getAll(@RequestParam(value = "search", defaultValue = "") String search,
                                              @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ingredientService.getAllBySearch(search, pageable);
    }
    @JsonView(IngredientViews.Full.class)
    @GetMapping("/{id}")
    public IngredientResponse getById(@PathVariable UUID id) {
        return IngredientResponse.fromIngredient(ingredientService.findById(id));
    }
    @JsonView(IngredientViews.Full.class)
    @PostMapping("/")
    public ResponseEntity<IngredientResponse> create(
            @JsonView(IngredientViews.Full.class)
            @Valid @RequestBody IngredientRequest ingredientRequest) {
        Ingredient created = Ingredient.builder()
                .name(ingredientRequest.getName())
                .build();

        if(!ingredientRequest.getImg().isEmpty())
            created.setImg(ingredientRequest.getImg());

        ingredientService.save(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(IngredientResponse.fromIngredient(created));
    }
    @JsonView(IngredientViews.Img.class)
    @PutMapping("/img/{id}")
    public IngredientResponse changeImg(@RequestPart("file") MultipartFile file, @PathVariable UUID id){
        return ingredientService.changeImg(file,id);
    }

    @JsonView(IngredientViews.Name.class)
    @PutMapping("/{id}")
    public IngredientResponse update(@PathVariable UUID id,
                                     @JsonView(IngredientViews.Name.class)
                                     @Valid @RequestBody IngredientRequest ingredientRequest){
        return ingredientService.update(ingredientRequest,id);
    }
}

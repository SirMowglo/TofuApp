import { Component, OnInit } from '@angular/core';
import { mergeMap, tap } from 'rxjs';
import { IngredientResponse } from 'src/app/models/ingredient.interface';
import {
  RecipeDetailsResponse,
  RecipeResponse,
} from 'src/app/models/recipe.interface';
import { IngredientService } from 'src/app/services/ingredient.service';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css'],
})
export class RecipesComponent implements OnInit {
  recipeList: RecipeResponse[] = [];
  ingredientList: IngredientResponse[] = [];
  recipeDetails = {} as RecipeDetailsResponse;
  indexRecipes = 0;
  indexIngredient = 0;

  totalPagesRecipes = 0;
  totalPagesIngredient = 0;
  
  //TODO Posibilidad de añadir una receta
  //TODO Posibilidad de borrar una receta seleccionada
  //TODO Posibilidad de editar una receta seleccionada

  constructor(
    private recipeService: RecipeService,
    private ingredientService: IngredientService
  ) {}

  ngOnInit(): void {
    this.getRecipes();
  }

  getRecipes() {
    this.recipeService
      .getRecipesBySearch('', this.indexRecipes)
      .subscribe((res) => {
        if (res.totalPages >= this.indexRecipes) {
          this.recipeList = res.content;
          this.totalPagesRecipes = res.totalPages - 1;
        }
      });
  }

  onScrollRecipe() {
    if (this.totalPagesRecipes > this.indexRecipes) {
      this.indexRecipes++;
      this.recipeService
        .getRecipesBySearch('', this.indexRecipes)
        .subscribe((res) => {
          this.recipeList.push(...res.content);
        });
    }
  }
  getRecipeIngredients(recipe: RecipeResponse) {
    this.ingredientService.getRecipeIngredients(recipe).subscribe((res) => {
      if (res.totalPages >= this.indexRecipes) {
        this.ingredientList = res.content;
        this.totalPagesIngredient = res.totalPages - 1
      }
    });
  }

  onScrollRecipeIngredient() {
    if (this.totalPagesIngredient > this.indexIngredient) {
      this.indexIngredient++;
      this.ingredientService
        .getIngredientBySearch('', this.indexIngredient)
        .subscribe((res) => {
          this.ingredientList.push(...res.content);
        });
    }
  }
  getRecipeDetails(recipe: RecipeResponse) {
    this.recipeService.getRecipeDetailsById(recipe.id).subscribe((res) => {
      this.recipeDetails = res;
    });
  }
}

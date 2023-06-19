import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { mergeMap, tap } from 'rxjs';
import { AddIngredientTorecipeDialogComponent } from 'src/app/components/dialogs/add-ingredient-torecipe-dialog/add-ingredient-torecipe-dialog.component';
import { AddRecipeDialogComponent } from 'src/app/components/dialogs/add-recipe-dialog/add-recipe-dialog.component';
import { IngredientResponse } from 'src/app/models/ingredient.interface';
import {
  RecipeDetailsResponse,
  RecipeResponse,
} from 'src/app/models/recipe.interface';
import { Step } from 'src/app/models/step.interface';
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
  stepList: Step[] = [];
  indexRecipes = 0;
  indexIngredient = 0;

  totalPagesRecipes = 0;
  totalPagesIngredient = 0;
  
  //TODO Posibilidad de borrar una receta seleccionada
  //TODO Posibilidad de editar una receta seleccionada

  constructor(
    private recipeService: RecipeService,
    private ingredientService: IngredientService,
    public dialog: MatDialog,
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
      this.stepList = res.steps.sort((a, b) =>
      a.stepNumber < b.stepNumber ? -1 : 1
    );
    });
  }

  openAddRecipeDialog(): void{
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = true;
    dialogConfig.panelClass = 'outlined_box_4px';

    this.dialog.open(AddRecipeDialogComponent, dialogConfig);
  }

  openAddIngredientToRecipeDialog():void{
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = false;
    dialogConfig.panelClass = 'outlined_box_4px';
    dialogConfig.data = {
      recipeId: this.recipeDetails.id
    }

    this.dialog.open(AddIngredientTorecipeDialogComponent, dialogConfig);
  }
}

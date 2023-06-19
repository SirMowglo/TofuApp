import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import {
  IngredientAmountRequest,
  IngredientResponse,
} from 'src/app/models/ingredient.interface';
import { IngredientService } from 'src/app/services/ingredient.service';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-add-ingredient-torecipe-dialog',
  templateUrl: './add-ingredient-torecipe-dialog.component.html',
  styleUrls: ['./add-ingredient-torecipe-dialog.component.css'],
})
export class AddIngredientTorecipeDialogComponent implements OnInit {
  ingredientList: IngredientResponse[] = [];
  indexIngredients = 0;
  totalPages = 0;
  selectedIngredient = {} as IngredientResponse;

  formGroup = new FormGroup({
    unit: new FormControl('', [Validators.required, Validators.maxLength(12)]),
    amount: new FormControl(0.0, [Validators.required]),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private ingredientService: IngredientService,
    private recipeService: RecipeService,
    private router: Router,
    public dialogRef: MatDialogRef<AddIngredientTorecipeDialogComponent>
  ) {}

  ngOnInit(): void {
    this.getIngredients();
  }
  onScrollIngredients() {
    if (this.totalPages > this.indexIngredients) {
      this.indexIngredients++;
      this.ingredientService
        .getIngredientBySearch('', this.indexIngredients)
        .subscribe((res) => {
          this.ingredientList.push(...res.content);
        });
    }
  }

  getIngredients() {
    this.ingredientService
      .getIngredientBySearch('', this.indexIngredients)
      .subscribe((res) => {
        if (res.totalPages >= this.indexIngredients) {
          this.ingredientList = res.content;
          this.totalPages = res.totalPages - 1;
        }
      });
  }
  getSelectedIngredient(ingredient: IngredientResponse) {
    this.selectedIngredient = ingredient;
  }

  addIngredient() {
    const ingredientAmountRequest: IngredientAmountRequest = {
      unit: this.formGroup.value.unit ?? '',
      amount: this.formGroup.value.amount ?? 0.0,
    };

    this.recipeService
      .addIngredientToRecipe(
        this.data.recipeId,
        this.selectedIngredient.id,
        ingredientAmountRequest
      )
      .subscribe(() => {
        this.reloadCurrentRoute();
        this.dialogRef.close();
      });
  }
  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}

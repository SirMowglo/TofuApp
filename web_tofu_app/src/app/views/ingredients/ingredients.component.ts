import { DialogConfig } from '@angular/cdk/dialog';
import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddIngredientDialogComponent } from 'src/app/components/dialogs/add-ingredient-dialog/add-ingredient-dialog.component';
import { IngredientResponse } from 'src/app/models/ingredient.interface';
import { IngredientService } from 'src/app/services/ingredient.service';

@Component({
  selector: 'app-ingredients',
  templateUrl: './ingredients.component.html',
  styleUrls: ['./ingredients.component.css'],
})
export class IngredientsComponent {
  ingredientList: IngredientResponse[] = [];
  indexIngredients = 0;
  totalPages = 0;
  selectedIngredient = {} as IngredientResponse;

  constructor(
    private ingredientService: IngredientService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getIngredients();
  }

  getIngredients() {
    this.ingredientService
      .getIngredientBySearch('', this.indexIngredients)
      .subscribe((res) => {
        if (res.totalPages >= this.indexIngredients) {
          this.ingredientList = res.content;
          this.totalPages = res.totalPages -1;
        }
      });
  }

  onScroll() {
    if (this.totalPages > this.indexIngredients) {
      this.indexIngredients++;
      this.ingredientService
        .getIngredientBySearch('', this.indexIngredients)
        .subscribe((res) => {
          this.ingredientList.push(...res.content);
        });
    }
  }

  getSelectedIngredient(ingredient: IngredientResponse) {
    this.selectedIngredient = ingredient;
  }

  openDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = true;
    dialogConfig.panelClass = 'outlined_box_4px';

    this.dialog.open(AddIngredientDialogComponent, dialogConfig);
  }
}

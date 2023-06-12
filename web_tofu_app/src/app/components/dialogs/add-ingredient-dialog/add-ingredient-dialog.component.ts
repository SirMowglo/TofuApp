import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { IngredientRequest } from 'src/app/models/ingredient.interface';
import { IngredientService } from 'src/app/services/ingredient.service';

@Component({
  selector: 'app-add-ingredient-dialog',
  templateUrl: './add-ingredient-dialog.component.html',
  styleUrls: ['./add-ingredient-dialog.component.css'],
})
export class AddIngredientDialogComponent {
  formGroup = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.maxLength(64)]),
    description: new FormControl('', [
      Validators.required,
      Validators.maxLength(254),
    ]),
  });

  constructor(
    private ingredientService: IngredientService,
    private router: Router,
    public dialogRef: MatDialogRef<AddIngredientDialogComponent>,

  ) {}

  createIngredient() {
    const ingredientRequest: IngredientRequest = {
      name: this.formGroup.value.name ?? '',
      description: this.formGroup.value.description ?? '',
    };

    this.ingredientService.createIngredient(ingredientRequest).subscribe(() => {
      this.reloadCurrentRoute();
      this.dialogRef.close()

    });
  }
  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}

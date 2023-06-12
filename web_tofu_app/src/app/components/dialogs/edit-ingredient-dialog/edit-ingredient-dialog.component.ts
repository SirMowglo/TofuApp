import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { IngredientRequest, IngredientResponse } from 'src/app/models/ingredient.interface';
import { IngredientService } from 'src/app/services/ingredient.service';

@Component({
  selector: 'app-edit-ingredient-dialog',
  templateUrl: './edit-ingredient-dialog.component.html',
  styleUrls: ['./edit-ingredient-dialog.component.css']
})
export class EditIngredientDialogComponent {
  formGroup = new FormGroup({
    name: new FormControl('', [Validators.maxLength(64)]),
    description: new FormControl('', [Validators.maxLength(254),]),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: IngredientResponse,
    private ingredientService: IngredientService,
    private router: Router,
    public dialogRef: MatDialogRef<EditIngredientDialogComponent>,

  ) {}

  editIngredient(){
    const ingredientRequest: IngredientRequest = {
      name: this.formGroup.value.name ?? '',
      description: this.formGroup.value.description ?? '',
    };

    this.ingredientService.updateIngredient(ingredientRequest, this.data.id)
    .subscribe(()=>{
      this.reloadCurrentRoute()
      this.dialogRef.close()
    })
  }

  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}

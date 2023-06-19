import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { RecipeRequest } from 'src/app/models/recipe.interface';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-add-recipe-dialog',
  templateUrl: './add-recipe-dialog.component.html',
  styleUrls: ['./add-recipe-dialog.component.css']
})
export class AddRecipeDialogComponent {
  
  formGroup = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.maxLength(64)]),
    description: new FormControl('', [Validators.required, Validators.maxLength(254)]),
    prepTime: new FormControl(0,[Validators.required, Validators.max(1000)])
  });

  constructor(
    private recipeService: RecipeService,
    private router: Router,
    public dialogRef: MatDialogRef<AddRecipeDialogComponent>,
  ){}

  createRecipe(){
    const recipeRequest: RecipeRequest = {
      name: this.formGroup.value.name ?? '',
      description: this.formGroup.value.description  ?? '',
      prepTime: this.formGroup.value.prepTime ?? 0,
      type: "meal"
    }

    this.recipeService.createRecipe(recipeRequest).subscribe(()=>{
      this.reloadCurrentRoute();
      this.dialogRef.close();
    })
  }

  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}

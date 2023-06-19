import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { StepRequest } from 'src/app/models/step.interface';
import { StepService } from 'src/app/services/step.service';

@Component({
  selector: 'app-add-step-to-recipe-dialog',
  templateUrl: './add-step-to-recipe-dialog.component.html',
  styleUrls: ['./add-step-to-recipe-dialog.component.css']
})
export class AddStepToRecipeDialogComponent {
  formGroup = new FormGroup({
    description: new FormControl('', [
      Validators.required,
      Validators.maxLength(254),
    ]),
  });
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private stepService: StepService,
    private router: Router,
    public dialogRef: MatDialogRef<AddStepToRecipeDialogComponent>,

  ) {}

  createStep(){
    const stepRequest: StepRequest = {
      description: this.formGroup.value.description ?? '',
    };

    this.stepService
      .createStep(
        this.data.recipeId,
        stepRequest
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

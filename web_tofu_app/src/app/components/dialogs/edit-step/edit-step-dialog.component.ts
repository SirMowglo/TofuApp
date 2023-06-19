import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Step, StepRequest } from 'src/app/models/step.interface';
import { StepService } from 'src/app/services/step.service';

@Component({
  selector: 'app-edit-step',
  templateUrl: './edit-step-dialog.component.html',
  styleUrls: ['./edit-step-dialog.component.css'],
})
export class EditStepDialogComponent {
  formGroup = new FormGroup({
    description: new FormControl('', [
      Validators.required,
      Validators.maxLength(254),
    ]),
  });
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Step,
    private stepService: StepService,
    private router: Router,
    public dialogRef: MatDialogRef<EditStepDialogComponent>
  ) {}

  editStep() {
    const stepRequest: StepRequest = {
      description: this.formGroup.value.description ?? '',
    };
    this.stepService.editStep(this.data.id, stepRequest).subscribe(() => {
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

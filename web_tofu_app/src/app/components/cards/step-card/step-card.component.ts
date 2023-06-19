import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Step } from 'src/app/models/step.interface';
import { StepService } from 'src/app/services/step.service';
import { EditStepDialogComponent } from '../../dialogs/edit-step/edit-step-dialog.component';

@Component({
  selector: 'app-step-card',
  templateUrl: './step-card.component.html',
  styleUrls: ['./step-card.component.css'],
})
export class StepCardComponent {
  @Input() step: Step = {} as Step;

  constructor(
    private stepService: StepService,
    private router: Router,
    public dialog: MatDialog
  ) {}

  deleteStep() {
    this.stepService.deleteStep(this.step.id).subscribe(() => {
      this.reloadCurrentRoute();
    });
  }

  openEditStepDialog() {
    const dialogConfig =new MatDialogConfig();

    dialogConfig.panelClass = 'outlined_box_4px';
    dialogConfig.data = this.step

    this.dialog.open(EditStepDialogComponent, dialogConfig)
  }

  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}

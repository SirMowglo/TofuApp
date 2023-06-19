import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { RecipeDetailsResponse } from 'src/app/models/recipe.interface';
import { UserResponse } from 'src/app/models/user.interface';
import { FileService } from 'src/app/services/file.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { AddStepToRecipeDialogComponent } from '../../dialogs/add-step-to-recipe-dialog/add-step-to-recipe-dialog.component';
import { Step } from 'src/app/models/step.interface';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css'],
})
export class RecipeDetailsComponent implements OnInit {
  @Input() recipe: RecipeDetailsResponse = {} as RecipeDetailsResponse;

  private _author = new BehaviorSubject<UserResponse>({} as UserResponse);
  @Input() set author(value: UserResponse) {
    this._author.next(value);
  }
  get author() {
    return this._author.getValue();
  }

  @Input() stepList: Step[] = [];
  recipeImgData = '';
  authorAvatarData = '';

  constructor(
    private fileService: FileService,
    private recipeService: RecipeService,
    private router: Router,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this._author.subscribe((val) => {
      this.getAuthorImage(val);
    });
    this.getRecipeImage(this.recipe.img);
  }
  getAuthorImage(val: UserResponse) {
    if (val.avatar != null) {
      this.fileService
        .getData(val.avatar)
        .subscribe((res) => (this.authorAvatarData = res));
    }
  }

  getRecipeImage(val: string) {
    if (val != null) {
      this.fileService
        .getData(val)
        .subscribe((res) => (this.recipeImgData = res));
    }
  }

  openAddStepDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.panelClass = 'outlined_box_4px';
    dialogConfig.data = {
      recipeId: this.recipe.id,
    };

    this.dialog.open(AddStepToRecipeDialogComponent, dialogConfig);
  }

  deleteCurrentRecipe() {
    this.recipeService.deleteRecipeById(this.recipe.id).subscribe(() => {
      this.reloadCurrentRoute();
    });
  }
  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}

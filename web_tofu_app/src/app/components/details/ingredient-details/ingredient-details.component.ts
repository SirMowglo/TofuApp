import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { IngredientResponse } from 'src/app/models/ingredient.interface';
import { UserResponse } from 'src/app/models/user.interface';
import { FileService } from 'src/app/services/file.service';
import { IngredientService } from 'src/app/services/ingredient.service';
import { EditIngredientDialogComponent } from '../../dialogs/edit-ingredient-dialog/edit-ingredient-dialog.component';

@Component({
  selector: 'app-ingredient-details',
  templateUrl: './ingredient-details.component.html',
  styleUrls: ['./ingredient-details.component.css'],
})
export class IngredientDetailsComponent {
  @Input() ingredient: IngredientResponse = {} as IngredientResponse;

  private _author = new BehaviorSubject<UserResponse>({} as UserResponse);
  @Input() set author(value: UserResponse) {
    this._author.next(value);
  }
  get author() {
    return this._author.getValue();
  }

  ingredientImgData = '';
  authorAvatarData = '';

  constructor(
    private fileService: FileService,
    private ingredientService: IngredientService,
    private router: Router,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this._author.subscribe((val) => {
      this.getAuthorImage(val);
    });
    this.getIngredientImage(this.ingredient.img);
  }

  getAuthorImage(val: UserResponse) {
    if (val.avatar != null) {
      this.fileService
        .getData(val.avatar)
        .subscribe((res) => (this.authorAvatarData = res));
    }
  }

  getIngredientImage(val: string) {
    if (val != null) {
      this.fileService
        .getData(val)
        .subscribe((res) => (this.ingredientImgData = res));
    }
  }

  deleteCurrentIngredient() {
    this.ingredientService
      .deleteIngredientById(this.ingredient.id)
      .subscribe(() => {
        this.reloadCurrentRoute();
      });
  }

  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }

  openDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = true;
    dialogConfig.panelClass = 'outlined_box_4px';
    dialogConfig.data = this.ingredient,
    

    this.dialog.open(EditIngredientDialogComponent, dialogConfig);
  }
}

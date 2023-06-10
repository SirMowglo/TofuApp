import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { HeaderComponent } from 'src/app/components/header/header.component';
import { FooterComponent } from 'src/app/components/footer/footer.component';
import { SidebarComponent } from 'src/app/components/sidebar/sidebar.component';
import { CustomButtonComponent } from 'src/app/components/custom-button/custom-button.component';
import { CustomInputComponent } from 'src/app/components/custom-input/custom-input.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material.module';
import { RecipeCardComponent } from 'src/app/components/cards/recipe-card/recipe-card.component';
import { IngredientCardComponent } from 'src/app/components/cards/ingredient-card/ingredient-card.component';
import { UserDetailsComponent } from 'src/app/components/details/user-details/user-details.component';
import { RecipeDetailsComponent } from 'src/app/components/details/recipe-details/recipe-details.component';
import { StepCardComponent } from 'src/app/components/cards/step-card/step-card.component';
import { UserCardComponent } from 'src/app/components/cards/user-card/user-card.component';
import { IngredientDetailsComponent } from 'src/app/components/details/ingredient-details/ingredient-details.component';
import { AddIngredientDialogComponent } from 'src/app/components/dialogs/add-ingredient-dialog/add-ingredient-dialog.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    CustomButtonComponent,
    CustomInputComponent,
    RecipeCardComponent,
    IngredientCardComponent,
    UserDetailsComponent,
    RecipeDetailsComponent,
    IngredientDetailsComponent,
    AddIngredientDialogComponent,
    StepCardComponent,
    UserCardComponent,
  ],
  imports: [
    CommonModule,
    SharedRoutingModule,
    ReactiveFormsModule,
    MaterialModule,   
    InfiniteScrollModule,

  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    CustomButtonComponent,
    CustomInputComponent,
    RecipeCardComponent,
    IngredientCardComponent,
    UserDetailsComponent,
    RecipeDetailsComponent,
    IngredientDetailsComponent,
    AddIngredientDialogComponent,
    StepCardComponent,
    UserCardComponent,
    CommonModule,
    SharedRoutingModule,
    ReactiveFormsModule,
    MaterialModule,   
    InfiniteScrollModule,
  ],
})
export class SharedModule {}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IngredientsRoutingModule } from './ingredients-routing.module';
import { IngredientsComponent } from './ingredients.component';
import { SharedModule } from 'src/app/modules/shared/shared.module';


@NgModule({
  declarations: [
    IngredientsComponent
  ],
  imports: [
    CommonModule,
    IngredientsRoutingModule,
    SharedModule,
  ]
})
export class IngredientsModule { }

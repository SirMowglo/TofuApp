import { NgModule } from '@angular/core';

import { IngredientsRoutingModule } from './ingredients-routing.module';
import { IngredientsComponent } from './ingredients.component';
import { SharedModule } from 'src/app/modules/shared/shared.module';


@NgModule({
  declarations: [
    IngredientsComponent
  ],
  imports: [
    IngredientsRoutingModule,
    SharedModule,
  ]
})
export class IngredientsModule { }

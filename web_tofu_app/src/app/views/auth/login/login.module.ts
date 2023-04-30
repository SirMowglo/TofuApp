import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';
import { MaterialModule } from 'src/app/modules/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomInputComponent } from 'src/app/components/custom-input/custom-input.component';
import { CustomButtonComponent } from 'src/app/components/custom-button/custom-button.component';


@NgModule({
  declarations: [
    LoginComponent,
    CustomInputComponent,
    CustomButtonComponent
  ],
  imports: [
    CommonModule,
    LoginRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class LoginModule { }

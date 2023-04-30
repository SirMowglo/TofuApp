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

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    CustomButtonComponent,
    CustomInputComponent,
  ],
  imports: [
    CommonModule,
    SharedRoutingModule,
    ReactiveFormsModule,
    MaterialModule,
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    CustomButtonComponent,
    CustomInputComponent,
  ],
})
export class SharedModule {}

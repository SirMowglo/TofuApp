import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginModule } from './views/auth/login/login.module';

import { SharedModule } from './modules/shared/shared.module';
import { HttpErrorInterceptor } from './services/interceptors/http-error.interceptor';
import { AutofocusDirective } from './directives/autofocus.directive';
import { AuthInterceptor } from './services/interceptors/auth.interceptor';
import { MaterialModule } from './modules/material.module';
import { LoadingInterceptor } from './services/interceptors/loading.interceptor';
import { AddRecipeDialogComponent } from './components/dialogs/add-recipe-dialog/add-recipe-dialog.component';
import { AddIngredientTorecipeDialogComponent } from './components/dialogs/add-ingredient-torecipe-dialog/add-ingredient-torecipe-dialog.component';
import { AddStepToRecipeDialogComponent } from './components/dialogs/add-step-to-recipe-dialog/add-step-to-recipe-dialog.component';
import { EditStepDialogComponent } from './components/dialogs/edit-step/edit-step-dialog.component';
@NgModule({
  declarations: [AppComponent, AutofocusDirective],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LoginModule,
    SharedModule,
    HttpClientModule,
    MaterialModule,
    
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

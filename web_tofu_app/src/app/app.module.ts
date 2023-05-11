import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginModule } from './views/auth/login/login.module';

import { SharedModule } from './modules/shared/shared.module';
import { HttpErrorInterceptor } from './services/http-error.interceptor';
import { AutofocusDirective } from './directives/autofocus.directive';
import { UserDetailsComponent } from './components/details/user-details/user-details.component';

@NgModule({
  declarations: [
    AppComponent,
     AutofocusDirective,
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LoginModule,
    SharedModule,
    HttpClientModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

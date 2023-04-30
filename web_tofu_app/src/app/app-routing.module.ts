import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CheckLoginGuard } from './guards/check-login.guard';

const routes: Routes = [
  {
    path: 'notFound',
    loadChildren: () =>
      import('./views/not-found/not-found.module').then(
        (m) => m.NotFoundModule
      ),
  },
  {
    path: 'home',
    loadChildren: () =>
      import('./views/home/home.module').then((m) => m.HomeModule),
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./views/auth/login/login.module').then((m) => m.LoginModule),
      canActivate:[CheckLoginGuard],
  },
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "**", redirectTo: "", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

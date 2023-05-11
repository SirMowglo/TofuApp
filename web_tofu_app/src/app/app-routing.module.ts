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
    canActivate: [CheckLoginGuard],
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'users',
    loadChildren: () =>
      import('./views/users/users.module').then((m) => m.UsersModule),
  },
  {
    path: 'recipes',
    loadChildren: () =>
      import('./views/recipes/recipes.module').then((m) => m.RecipesModule),
  },
  {
    path: 'ingredients',
    loadChildren: () =>
      import('./views/ingredients/ingredients.module').then(
        (m) => m.IngredientsModule
      ),
  },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

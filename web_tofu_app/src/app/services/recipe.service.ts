import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import {
  RecipeDetailsResponse,
  RecipeRequest,
  RecipeResponse,
} from '../models/recipe.interface';
import { Page } from '../models/page.interface';
import {
  IngredientAmountRequest,
  RecipeIngredientResponse,
} from '../models/ingredient.interface';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  constructor(private http: HttpClient) {}

  public getRecipesBySearch(
    search: string,
    page = 0
  ): Observable<Page<RecipeResponse>> {
    return this.http.get<Page<RecipeResponse>>(
      `${environment.API_URL}/recipe?search=${search}&page=${page}`
    );
  }

  public getRecipesByAuthor(
    username: string,
    page = 0
  ): Observable<Page<RecipeResponse>> {
    return this.http.get<Page<RecipeResponse>>(
      `${environment.API_URL}/recipe/author/${username}?page=${page}`
    );
  }

  public getRecipeDetailsById(
    recipeId: string
  ): Observable<RecipeDetailsResponse> {
    return this.http.get<RecipeDetailsResponse>(
      `${environment.API_URL}/recipe/${recipeId}`
    );
  }

  public createRecipe(
    recipeRequest: RecipeRequest
  ): Observable<RecipeResponse> {
    return this.http.post<RecipeResponse>(
      `${environment.API_URL}/recipe`,
      recipeRequest
    );
  }

  public addIngredientToRecipe(
    recipeId: string,
    ingredientId: string,
    ingredientAmount: IngredientAmountRequest
  ): Observable<RecipeIngredientResponse> {
    return this.http.post<RecipeIngredientResponse>(
      `${environment.API_URL}/recipe/${recipeId}/ingredient/${ingredientId}`,
      ingredientAmount
    );
  }

  public deleteRecipeById(id: string): Observable<unknown> {
    return this.http.delete<unknown>(`${environment.API_URL}/recipe/${id}`);
  }
}

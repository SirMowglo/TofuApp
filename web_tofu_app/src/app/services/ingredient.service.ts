import { Injectable } from '@angular/core';
import { IngredientRequest, IngredientResponse } from '../models/ingredient.interface';
import { Page } from '../models/page.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { RecipeResponse } from '../models/recipe.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class IngredientService {
  constructor(private http: HttpClient) {}

  public getIngredientsByAuthor(
    username: string,
    page = 0
  ): Observable<Page<IngredientResponse>> {
    return this.http.get<Page<IngredientResponse>>(
      `${environment.API_URL}/ingredient/author/${username}?page=${page}`
    );
  }

  public getRecipeIngredients(recipe: RecipeResponse, page = 0) {
    return this.http.get<Page<IngredientResponse>>(
      `${environment.API_URL}/ingredient/recipe/${recipe.id}?page=${page}`
    );
  }

  public getIngredientBySearch(
    search: string,
    page = 0
  ): Observable<Page<IngredientResponse>> {
    return this.http.get<Page<IngredientResponse>>(
      `${environment.API_URL}/ingredient?search=${search}&page=${page}`
    );
  }

  public deleteIngredientById(id:string): Observable<any>{
   return this.http.delete<any>(`${environment.API_URL}/ingredient/${id}`)
  }

  public createIngredient(ingredientRequest: IngredientRequest): Observable<IngredientResponse> {
    return this.http
      .post<IngredientResponse>(`${environment.API_URL}/ingredient`, ingredientRequest)
}}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RecipeResponse } from '../models/recipe.interface';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  constructor(private http: HttpClient) {}

  public getRecipesBySearch(search: string, page =1): Observable<RecipeResponse[]> {
    if(search !=null){
      return this.http.get<RecipeResponse[]>(
        `${environment.API_URL}/recipe?search=${search}&page=${page}`
      );
    }else{
      return this.http.get<RecipeResponse[]>(
        `${environment.API_URL}/recipe?page=${page}`)
    }
  }
}

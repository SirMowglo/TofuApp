import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  constructor(private http: HttpClient) {}

  public getRecipesBySearch(search: string, page: number) {
    return this.http.get(
      `${environment.API_URL}/recipe?search=${search}&page=${page}`
    );
  }
}

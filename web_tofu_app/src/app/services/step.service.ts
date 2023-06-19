import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Step, StepRequest } from '../models/step.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StepService {
  constructor(private http: HttpClient) {}

  public createStep(
    recipeId: string,
    stepRequest: StepRequest
  ): Observable<Step> {
    return this.http.post<Step>(
      `${environment.API_URL}/step/recipe/${recipeId}`,
      stepRequest
    );
  }

  public deleteStep(stepId: string): Observable<unknown> {
    return this.http.delete<unknown>(`${environment.API_URL}/step/${stepId}`);
  }

  public editStep(stepId:string, stepRequest: StepRequest): Observable<Step>{
    return this.http.put<Step>(`${environment.API_URL}/step/${stepId}`,stepRequest)
  }
}

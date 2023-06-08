import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserDetailsResponse, UserResponse } from '../models/user.interface';
import { Page } from '../models/page.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  public getCurrentUser(): Observable<UserDetailsResponse> {
    return this.http.get<UserDetailsResponse>(`${environment.API_URL}/user/me`);
  }

  public getUsersBySearch(
    search: string,
    page = 0
  ): Observable<Page<UserResponse>> {
    return this.http.get<Page<UserResponse>>(
      `${environment.API_URL}/user?search=${search}&page=${page}`
    );
  }
  public getUserDetailsByUsername(
    username: string
  ): Observable<UserDetailsResponse> {
    return this.http.get<UserDetailsResponse>(
      `${environment.API_URL}/user/${username}`
    );
  }
}

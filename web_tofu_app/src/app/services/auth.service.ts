import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, map, throwError } from 'rxjs';
import { JwtUserResponse, LoginRequest } from '../models/user.interface';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

const helper = new JwtHelperService();
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private router: Router) {
    this.checkToken();
  }

  get isLogged(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  login(loginRequest: LoginRequest): Observable<JwtUserResponse | void> {
    return this.http
      .post<JwtUserResponse>(`${environment.API_URL}/auth/login`, loginRequest)
      .pipe(
        map((res: JwtUserResponse) => {
          this.saveToken(res.token);
          this.loggedIn.next(true);
          return res;
        }),
        catchError((err) => this.handleError(err))
      );
  }
  logout(): void {
    localStorage.removeItem('token');
    this.loggedIn.next(false);
    this.router.navigate(['login'])
  }
  private checkToken(): void {
    const userToken = localStorage.getItem('token');
    const isExpired = helper.isTokenExpired(userToken);

    isExpired ? this.logout : this.loggedIn.next(true);
  }

  private saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  private handleError(err: any): Observable<never> {
    let errorMessage = 'An error has occured';
    if (err) {
      errorMessage = `Error: code ${err.message}`;
    }
    window.alert(errorMessage);
    return throwError(() => err);
  }
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, map, throwError } from 'rxjs';
import {
  CreateUserRequest,
  JwtResponse,
  JwtUserResponse,
  LoginRequest,
  RefreshTokenRequest,
  UserResponse,
} from '../models/user.interface';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

const helper = new JwtHelperService();
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(false);
  private loggedUsername =""

  get authUser(): string{
    return this.loggedUsername
  }

  constructor(private http: HttpClient, private router: Router) {}

  get isLogged(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  login(loginRequest: LoginRequest): Observable<JwtUserResponse | void> {
    return this.http
      .post<JwtUserResponse>(`${environment.API_URL}/auth/login`, loginRequest)
      .pipe(
        map((res: JwtUserResponse) => {
          this.saveToken(res.token, res.refreshToken);
          this.loggedIn.next(true);
          this.loggedUsername = res.username
          return res;
        }),
        catchError((error) => {
          return throwError(() => error);
        })
      );
  }
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('refresh_token');
    this.loggedIn.next(false);
    this.router.navigate(['login']);
  }

  autologin(): void {
    this.loggedIn.next(true);
    this.router.navigate(['home']);
  }
  checkToken(): void {
    const userToken = localStorage.getItem('token');
    const refreshToken = localStorage.getItem('refresh_token');

    const isExpired = helper.isTokenExpired(userToken);
    if (refreshToken) {
      isExpired
        ? this.refreshToken(refreshToken)
            .pipe(
              map((res) => {
                this.saveToken(res.token, res.refreshToken);
                return res;
              }),
              catchError((error) => {
                return throwError(() => error);
              })
            )
            .subscribe((res) => {
              if (res) {
                this.autologin();
              }
            })
        : this.autologin();
    } else this.logout();
  }

  saveToken(token: string, refreshToken: string): void {
    localStorage.clear();
    localStorage.setItem('token', token);
    localStorage.setItem('refresh_token', refreshToken);
  }

  refreshToken(reft: string): Observable<JwtResponse> {
    const rt: RefreshTokenRequest = {
      refreshToken: reft,
    };
    console.log('Lmao');
    return this.http.post<JwtResponse>(
      `${environment.API_URL}/refreshtoken`,
      rt,
      httpOptions
    );
  }

  registerUser(request: CreateUserRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(
      `${environment.API_URL}/auth/register`,
      request
    );
  }
  registerAdmin(request: CreateUserRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(
      `${environment.API_URL}/auth/register/admin`,
      request
    );
  }
}

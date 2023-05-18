import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, map, mergeMap, timer } from 'rxjs';
import { JwtResponse, JwtUserResponse, LoginRequest, RefreshTokenRequest } from '../models/user.interface';
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

    setInterval(()=> { this.checkToken() }, 60 * 1000);
    console.log(this.loggedIn)
  }

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
          return res;
        })
      );
  }
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('refresh_token')
    this.loggedIn.next(false);
    this.router.navigate(['login']);
  }
  private checkToken(): void {
    const userToken = localStorage.getItem('token');
    const isExpired = helper.isTokenExpired(userToken);

    isExpired ? this.refreshToken() : this.loggedIn.next(true);
  }

  private saveToken(token: string, refreshToken : string): void {
    localStorage.setItem('token', token);
    localStorage.setItem('refresh_token', refreshToken);
  }

  refreshToken() : Observable<JwtResponse | void>{
    const rt : RefreshTokenRequest = {
      refreshToken: localStorage.getItem('refresh_token') ?? ""
    }
    return this.http.post<JwtResponse>(`${environment.API_URL}/refreshtoken`, rt)
    .pipe(
      map((res: JwtResponse) => {
        this.saveToken(res.token, res.refreshToken);
        this.loggedIn.next(true);
      })
      //Mirar esto junto al Interceptor
    )
  }
}

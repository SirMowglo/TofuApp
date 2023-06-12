import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError } from 'rxjs';
import { AuthService } from '../auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    let authReq = request;
    const token = localStorage.getItem('token');
    if (token != null) {
      authReq = this.addTokenToRequest(request, token);
    }

    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 && !request.url.includes('/auth/login')) {
          return this.handleAuthError(authReq, next);
        }
        return throwError(() => error);
      })
    );
  }

  private addTokenToRequest(
    request: HttpRequest<any>,
    token: string
  ): HttpRequest<unknown> {
    console.log("añade token a request")
    return request.clone({
      setHeaders: {
        'authorization': `Bearer ${token}`,
        // 'content-type': 'application/json',
      },
      withCredentials: true,
    });
  }

  private handleAuthError(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    if (!this.isRefreshing) {
      console.log("is refreshing")
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      const refreshToken = localStorage.getItem('refresh_token')
      if(refreshToken){
      return this.authService.refreshToken(refreshToken).pipe(
        switchMap((res) => {
          console.log("Adding token to request")
          this.isRefreshing = false;

          this.authService.saveToken(res.token, res.refreshToken);
          this.refreshTokenSubject.next(res.token);

          const newRequest = this.addTokenToRequest(request, res.token);
          return next.handle(newRequest);
        }),
        catchError((error) => {
          console.log("Error en el handle")
          this.isRefreshing = false;
          return throwError(() => error);
        })
      );}
    }
    return this.refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token) => next.handle(this.addTokenToRequest(request, token)))
    );
  }
}

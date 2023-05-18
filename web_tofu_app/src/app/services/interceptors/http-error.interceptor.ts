import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, catchError, switchMap, throwError } from 'rxjs';

import { ServerError } from 'src/app/models/error.interface';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  constructor(private router: Router, private authService: AuthService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if ('subErrors' in error.error) {
          const err: ServerError = error.error;
          alert(err.subErrors[0].message);
        } else {
          alert(error.error.message);
        }

        if (error.status === 401 || error.status === 403) {
          if (!this.isRefreshing) {
            this.isRefreshing = true;
            if (this.authService.isLogged) {
              console.log("refresh")
              this.authService.refreshToken().pipe(
                switchMap(() => {
                  this.isRefreshing = false;

                  return next.handle(request);
                }),
                catchError((error) => {
                  this.isRefreshing = false;

                  if (error.status == '403') {
                    this.authService.logout();
                  }

                  return throwError(() => error);
                })
              );
            }
          }
          return next.handle(request);
        }
        return throwError(() => error.error);
      })
    );
  }
}

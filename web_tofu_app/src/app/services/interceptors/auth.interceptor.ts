import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    
    const token = localStorage.getItem('token');
    let req = request;

    if (token !== null) {
      req = request.clone({
        setHeaders: {
          authorization: `Bearer ${token}`,
        },
        withCredentials: true 
      });
    }
    console.log(request.headers);
    return next.handle(req);
  }
}

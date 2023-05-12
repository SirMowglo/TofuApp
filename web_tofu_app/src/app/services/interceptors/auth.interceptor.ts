import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const token : string = localStorage.getItem('token') || '';

    let req = request;

    if(token.trim().length !== 0){
      req = request.clone({
        setHeaders:{
          authorization: `Bearer ${token}`
        }
      })
    }

    return next.handle(req);
  }
}

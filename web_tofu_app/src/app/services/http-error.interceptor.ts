import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

import { ServerError } from 'src/app/models/error.interface'

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  // constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('HTTP Request Started')
    return next.handle(request)
    .pipe(
      catchError((error: HttpErrorResponse) => {

        if('subErrors' in error.error){
          const err: ServerError = error.error
          alert(err.subErrors[0].message)
        }else{
          alert(error.error.message);
        }

        return throwError(() => error.error);
      })
    );
  }
}



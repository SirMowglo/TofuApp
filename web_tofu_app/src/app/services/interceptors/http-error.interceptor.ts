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
import { Router } from '@angular/router';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('HTTP ERROR')
    return next.handle(request)
    .pipe(
      catchError((error: HttpErrorResponse) => {

        if('subErrors' in error.error){
          const err: ServerError = error.error
          alert(err.subErrors[0].message)
          if(error.status === 401){
            this.router.navigateByUrl('/login');
          }
        }else{
          alert(error.error.message);
        }

        return throwError(() => error.error);
      })
    );
  }
}



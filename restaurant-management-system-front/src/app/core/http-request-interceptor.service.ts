import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class HttpRequestInterceptorService  implements HttpInterceptor{
  token: string;

  constructor(private route: Router) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.token = localStorage.userData ? JSON.parse(localStorage.userData).token : null;

    const authReq = req.clone({
      headers: this.token ? req.headers.set(
        'Authorization',
        'Bearer ' + this.token
      ) : req.headers
    });
    return next.handle(authReq).pipe(tap(() => { },
      error => {
        if (error.error.status === 401) {
          console.log('interceptor error');
          localStorage.removeItem('userData');
          this.route.navigate(['/login']);
        }
      }

    )
    );

  }

}

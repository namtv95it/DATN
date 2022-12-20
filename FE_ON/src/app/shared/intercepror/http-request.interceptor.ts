import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HTTP_INTERCEPTORS,
  HttpHeaders,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StorageService} from "../service/storage.service";
import {Router} from "@angular/router";

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(private readonly storageService: StorageService,
              private readonly router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (this.storageService.isLoggedIn() && this.storageService.getExpiration()) {
      this.storageService.clearToken();
    }

    const userToken = this.storageService.getToken();
    let httpHeader = new HttpHeaders({
      // 'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Headers': 'Content-Type',
      'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
    });

    if (req.url.includes('https://online-gateway.ghn.vn')) {
      httpHeader = httpHeader.append('token', 'cff0fcca-5ddf-11ed-ad26-3a4226f77ff0')
    }

    if (userToken != null) {
      httpHeader = httpHeader.append('Authorization', 'Bearer ' + userToken)
    }

    req = req.clone({
      headers: httpHeader
    });
    return next.handle(req);
  }
}


export const httpInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true},
];

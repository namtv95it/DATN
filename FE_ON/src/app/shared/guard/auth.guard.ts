import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {StorageService} from "../service/storage.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(public readonly storageService: StorageService,
              public readonly router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (!this.storageService.isLoggedIn()) {
      void this.router.navigate(['/sign-in'], {queryParams: {redirectUrl: state.url}});
      return false;
    } else {
      return true;
    }
  }

}

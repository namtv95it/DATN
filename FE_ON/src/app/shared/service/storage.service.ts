import {Injectable} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

const USER_TOKEN = 'customer-token';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private jwtHelper = new JwtHelperService();

  constructor() {
  }

  /**Xoá token cũ ở Local Storage và lưu token mới*/
  public saveUserToken(userToken: any) {
    window.localStorage.removeItem(USER_TOKEN);
    window.localStorage.setItem(USER_TOKEN, JSON.stringify(userToken));
  }

  public getUserToken() {
    const userToken = window.localStorage.getItem(USER_TOKEN);
    if (userToken) {
      return JSON.parse(userToken);
    }
    return {};
  }

  public isLoggedIn() :boolean{
    const authToken = this.getUserToken();
    return authToken !== undefined && authToken?.token !== undefined;
  }

  getToken() {
    const authToken = this.getUserToken();
    return authToken !== null && authToken?.token !== null ? authToken?.token : null;
  }

  clearToken() {
    window.localStorage.removeItem(USER_TOKEN);
  }

  clean() {
    window.localStorage.clear();
  }

  reloadPage() {
    window.location.reload();
  }

  decode() {
    try {
      const decode = this.jwtHelper.decodeToken(this.getToken());
      return decode !== null ? decode : null;
    } catch (e) {
      this.clearToken();
      this.reloadPage();
    }
  }

  getIdFromToken() {
    const decode = this.decode();
    return decode !== null && decode?.role !== null ? decode?.id : null;
  }

  getRoleFromToken() {
    const decode = this.decode();
    return decode !== null && decode?.role !== null ? decode?.role : null;
  }

  getFullNameFromToken() {
    const decode = this.decode();
    return decode !== null && decode?.role !== null ? decode?.fullname : null;
  }

  getExpiration() {
    return this.jwtHelper.isTokenExpired(this.getToken());
  }

}

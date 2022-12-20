import {Injectable} from '@angular/core';
import {AuthApiService} from "./auth-api.service";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private readonly authApiService: AuthApiService) {
  }

  login(data: any) {
    return this.authApiService.login(data);
  }

  register(data: any) {
    return this.authApiService.register(data);
  }

  sendEmailForgotPassword(email: any, url: any) {
    return this.authApiService.sendEmailForgotPassword(email, url);
  }

  getCustomerByPasswordToken(passwordToken: any) {
    return this.authApiService.getCustomerByPasswordToken(passwordToken);
  }

  changePassword(data: any) {
    return this.authApiService.changePassword(data);
  }

}

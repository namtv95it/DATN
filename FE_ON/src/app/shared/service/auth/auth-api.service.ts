import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class AuthApiService {

  constructor(private readonly http: HttpClient) {
  }


  login(data: any) {
    return this.http.post(`${ApiConstrant.auth.login}`, data);
  }

  register(data: any) {
    return this.http.post(`${ApiConstrant.auth.register}`, data);
  }

  sendEmailForgotPassword(email:any, url:any) {
    return this.http.get(`${ApiConstrant.auth.forgotPassword}/send-email?email=${email}&url=${url}`);
  }

  getCustomerByPasswordToken(passwordToken: any) {
    return this.http.get(`${ApiConstrant.auth.forgotPassword}/get-password-token?password-token=${passwordToken}`);
  }

  changePassword(data: any){
    return this.http.post(`${ApiConstrant.auth.forgotPassword}/change-password`, data);
  }

}

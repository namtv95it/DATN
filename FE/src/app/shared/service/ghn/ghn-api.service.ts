import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Ghn } from '../../constants/Ghn';
import { ApiConstant } from '../../constants/ApiConstant';
import axios from 'axios';
import { Constant } from '../../constants/Constant';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GhnApiService {

  constructor(
      private http: HttpClient,
    
    ) { }

  createOrderGhn(data: any){
    return this.http.post(`${ApiConstant.ghn}/create`,data);
  }

  getOrderGhn(orderCode: any): Observable<any>{
    return this.http.post(`${ApiConstant.ghn}/detail`,orderCode);
  }
  //DEV
  // getDate(): Observable<any>{
  //   return this.http.get('https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shift/date');
  // }
  getDate(): Observable<any>{
    return this.http.get('https://online-gateway.ghn.vn/shiip/public-api/v2/shift/date');
  }

  //DEV
  // genToken(orderCode: any):Observable<any>{
  //   return this.http.post('https://dev-online-gateway.ghn.vn/shiip/public-api/v2/a5/gen-token',orderCode);
  // }

  genToken(orderCode: any):Observable<any>{
    return this.http.post('https://online-gateway.ghn.vn/shiip/public-api/v2/a5/gen-token',orderCode);
  }

  //DEV
  // cancelOrderGhn(orderCode: any): Observable<any>{
  //   return this.http.post('https://dev-online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel',orderCode);
  // }

  cancelOrderGhn(orderCode: any): Observable<any>{
    return this.http.post('https://online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel',orderCode);
  }

  getProvince() {
    return this.http.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/province');
  }

  getDistrict(provinceId: any):Observable<any>{
    return this.http.get(`https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=${provinceId}`)
  }

  getWard(districtId: any):Observable<any>{
    return  this.http.get(`https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=${districtId}`);
  }


  getService(data:any):Observable<any>{
    return  this.http.post("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services",data);
  }

  getShippingOrder(data:any):Observable<any>{
    return this.http.post("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee",data);
  }
}

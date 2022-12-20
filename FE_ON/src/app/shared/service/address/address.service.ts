import {Injectable} from '@angular/core';
import {ApiConstrant} from "../../constants/ApiConstrants.module";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private readonly http: HttpClient) {
  }

  getProvince() {
    return this.http.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/province');
  }

  getDistrict(provinceId: any) {
    return this.http.get(`https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=${provinceId}`);
  }

  getWard(districtId: any) {
    return this.http.get(`https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=${districtId}`);
  }

  getService(data: any) {
    return this.http.post('https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services', data);
  }

  getShippingOrder(data: any) {
    return this.http.post('https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee', data);
  }

  findAddressByCustomerId(customerId: any) {
    return this.http.get(`${ApiConstrant.address}/${customerId}`);
  }

  findAddressById(id: any) {
    return this.http.get(`${ApiConstrant.address}/address/${id}`);
  }

  createAddress(data: any) {
    return this.http.post(`${ApiConstrant.address}`, data);
  }

  updateAddress(id: number, data: any) {
    return this.http.put(`${ApiConstrant.address}/${id}`, data);
  }

  deleteAddress(id: number) {
    return this.http.delete(`${ApiConstrant.address}/${id}`);
  }

  findAddressByStatus(customerId: number) {
    return this.http.get(`${ApiConstrant.address}/status/${customerId}`);
  }
}

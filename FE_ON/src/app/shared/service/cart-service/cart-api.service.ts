import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class CartApiService {

  constructor(private readonly http: HttpClient) {
  }

  addToCart(data: any) {
    return this.http.post(`${ApiConstrant.cart}`, data);
  }

  updateCart(data: any) {
    return this.http.put(`${ApiConstrant.cart}`, data);
  }

  deleteCart(id: number) {
    return this.http.delete(`${ApiConstrant.cart}/${id}`);
  }

  findAllByCustomerId(customerId: any) {
    return this.http.get(`${ApiConstrant.cart}/${customerId}`);
  }

  deleteAllByCustomerId(customerId: any){
    return this.http.delete(`${ApiConstrant.cart}/delete-all/${customerId}`);
  }

}

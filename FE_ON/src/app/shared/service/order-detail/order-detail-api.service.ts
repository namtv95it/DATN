import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class OrderDetailApiService {

  constructor(private readonly http: HttpClient) {
  }

  createOrderDetail(data: any) {
    return this.http.post(`${ApiConstrant.orderDetail}`, data);
  }

  getAllOrderDatailByIdOrder(id: number) {
    return this.http.get(`${ApiConstrant.orderDetail}?id=${id}`)
  }

  getAllOrderDetailByCustomeAndOrder(idCustom: number, idOrder: number) {
    return this.http.get(`${ApiConstrant.orderDetail}/getByCusAndOrder?idCus=${idCustom}&idOrder=${idOrder}`)
  }

  updateOrderDetailExchange(data: any) {
    return this.http.put(`${ApiConstrant.orderDetail}`, data);
  }

  saveOrderDetailExchange(data: any) {
    return this.http.post(`${ApiConstrant.orderDetail}`, data);
  }

  getOrderDetailByOrderId(id: number) {
    return this.http.get(`http://localhost:8080/api/v1/orderDetail/${id}`)
  }

  getOrderDetailsInExchange(id: any) {
    return this.http.get(`${ApiConstrant.orderDetail}/${id}`);
  }
}

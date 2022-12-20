import { Injectable } from '@angular/core';
import {OrderApiService} from "./order-api.service";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private readonly orderApiService: OrderApiService) { }

  createOrder(data: any) {
    return this.orderApiService.createOrder(data);
  }

  getAllOrderByStatus( status: number , idCustome: number){
     return this.orderApiService.getAllOrderByStatus( status , idCustome ) ;
  }

  getAllOrder(idCustome: number) {
     return this.orderApiService.getAllOrder(idCustome) ;
  }

  updateStatusOrder( status: number , id: any ){
     return this.orderApiService.updateOrder( status , id) ;
  }
}

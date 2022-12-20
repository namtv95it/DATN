import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class OrderApiService {

  constructor(private readonly http: HttpClient) {
  }

  createOrder(data: any) {
    return this.http.post(`${ApiConstrant.order}`, data);
  }

  getAllOrderByStatus( status: number , idCustome: number ){
     return this.http.get(`${ApiConstrant.order}?status=${status}&id=${idCustome}`)
  }

  getAllOrder( idCustome: number ){
     return this.http.get(`${ApiConstrant.order}/getAll?id=${idCustome}`)
  }

  updateOrder( status: number , id: any ){
     return this.http.get(`${ApiConstrant.order}/updateStatus?status=${status}&&id=${id}`) ;
  }

}

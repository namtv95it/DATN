import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class ExchangeService {

  constructor(private readonly http: HttpClient) {
  }

  createExchange(data: any) {
    return this.http.post(`${ApiConstrant.exchange}`, data);
  }

  createExchangeImage(data: any) {
    return this.http.post(`${ApiConstrant.exchangeImage}`, data);
  }
}

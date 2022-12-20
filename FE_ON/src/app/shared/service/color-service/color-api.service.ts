import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class ColorApiService {

  constructor(private readonly http: HttpClient) { }


  findAllColorInProductDetails(productId: number){
    return this.http.get(`${ApiConstrant.color}/productId/${productId}`);
  }

  getAll(){
     return this.http.get(ApiConstrant.color ) ;
  }

}

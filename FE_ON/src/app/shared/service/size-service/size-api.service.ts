import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SizeApiService {

  constructor(private readonly http: HttpClient) { }


  findAllSizeInProductDetails(productId: number){
    return this.http.get(`${ApiConstrant.size}/productId/${productId}`);
  }

  getAll():Observable<any> {
    return this.http.get(ApiConstrant.size) ;
  }

}

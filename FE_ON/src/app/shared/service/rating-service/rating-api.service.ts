import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class RatingApiService {
  constructor( private http: HttpClient) {}

  createRating( rate: any){
     return this.http.post(`${ApiConstrant.rating}` , rate )
  }

  getAllRatingByIdCustome( id: number ){
     return this.http.get(`${ApiConstrant.rating}?id=${id}&status=3`)
  }

  getAvgRating(){
    return this.http.get(`${ApiConstrant.rating}/getAvgRating`)
  }

  getRatingPro( idPro: number , pageNo: number ) {
     return this.http.get(`${ApiConstrant.rating}/getRatingPro?idPro=${idPro}&pageNo=${pageNo}`)
  }
}

import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class RatingImageApiService {

  constructor( private http: HttpClient ) { }

  createRatingImage( rateImg: any){
    return this.http.post(`${ApiConstrant.ratingImage}` , rateImg )
  }

  getRatingImgByIdRating( url: any ){
    return this.http.get(`${ApiConstrant.ratingImage}/getRatingImg?${url}` )
  }
}

import { Injectable } from '@angular/core';
import {RatingApiService} from "./rating-api.service";

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  constructor( private ratingService: RatingApiService) {}

  getAllRatingByIdCustome( id: number  ){
     return this.ratingService.getAllRatingByIdCustome( id );
  }


  createRating( rate: any ){
     return this.ratingService.createRating(rate) ;
  }

  getArgRating(){
     return this.ratingService.getAvgRating() ;
  }

  getRatingPro( idPro: number , pageNo: number ) {
     return this.ratingService.getRatingPro( idPro , pageNo) ;
  }
}

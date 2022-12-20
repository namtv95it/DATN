import { Injectable } from '@angular/core';
import {RatingImageApiService} from "./rating-image-api.service";

@Injectable({
  providedIn: 'root'
})
export class RatingImageService {


  constructor( private ratingImgService: RatingImageApiService ) { }

  creatRatingImg( rateImg: any ){
     return this.ratingImgService.createRatingImage(rateImg) ;
  }

  getRatingImgByIdRating( list:any ){
     var a = ''
     for( let x of list ){
        a += 'rateImg=' + x.id + '&'
     }

     return this.ratingImgService.getRatingImgByIdRating(a) ;
  }
}

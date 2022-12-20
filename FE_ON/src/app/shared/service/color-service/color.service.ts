import { Injectable } from '@angular/core';
import {ColorApiService} from "./color-api.service";

@Injectable({
  providedIn: 'root'
})
export class ColorService {

  constructor(private readonly colorApiService: ColorApiService) { }


  findAllColorInProductDetails(productId: number){
    return this.colorApiService.findAllColorInProductDetails(productId);
  }

  getAllByColor() {
     return this.colorApiService.getAll() ;
  }

}

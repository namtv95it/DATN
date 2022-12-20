import {Injectable} from '@angular/core';
import {SizeApiService} from "./size-api.service";

@Injectable({
  providedIn: 'root'
})
export class SizeService {

  constructor(private readonly sizeApiService: SizeApiService) {
  }

  findAllSizeInProductDetails(productId: number) {
    return this.sizeApiService.findAllSizeInProductDetails(productId);
  }

  getAllSize() {
    return this.sizeApiService.getAll() ;
  }

}

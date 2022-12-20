import {Injectable} from '@angular/core';
import {ProductDetailApiService} from "./product-detail-api.service";

@Injectable({
  providedIn: 'root'
})
export class ProductDetailService {

  constructor(private readonly productDetailApi: ProductDetailApiService) {
  }

  getProductDetailByProductId(productId: number) {
    return this.productDetailApi.getProductDetailByProductId(productId);
  }

  findProductDetailBySizeAndColor(productId: any, sizeId: any, colorId: any) {
    return this.productDetailApi.findProductDetailBySizeAndColor(productId, sizeId, colorId);
  }
}

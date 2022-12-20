import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class ProductDetailApiService {

  constructor(private readonly http: HttpClient) {
  }

  getProductDetailByProductId(productId: number) {
    return this.http.get(`${ApiConstrant.productDetail}/detail/${productId}`);
  }

  findProductDetailBySizeAndColor(productId: any, sizeId: any, colorId: any) {
    return this.http.get(`${ApiConstrant.productDetail}/p-detail?productId=${productId}&sizeId=${sizeId}&colorId=${colorId}`)
  }

}

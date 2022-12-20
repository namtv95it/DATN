import {Injectable} from '@angular/core';
import {ProductApiService} from "./product-api.service";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private proApi: ProductApiService) {
  }

  getAllProduct() {
    return this.proApi.getAll();
  }

  getProductById(id: number) {
    return this.proApi.getById(id);
  }

  // Product Imgae
  getProductImageById(id: number) {
    return this.proApi.getProductImage(id);
  }

  getAllProByAllProperty(sizes: any, cate: any, color: any, max: number, min: number,
                         pageNo: number, pageSize: number, sortPrice: number) {
    var path = ''
    for (var x of sizes) {
      path += 'size=' + x + '&'
    }

    for (var x of cate) {
      path += 'category=' + x + '&'
    }

    for (var x of color) {
      path += 'color=' + x + '&'
    }

    if (max != 0 && min != 0) {
      path += 'max=' + max + '&'
      path += 'min=' + min + '&'
    }

    path += 'pageNo=' + pageNo + '&'
    path += 'pageSize=' + pageSize + '&'
    path += 'sortPrice=' + sortPrice

    return this.proApi.getProBySize(path);
  }

  getNewPro() {
    return this.proApi.getNewProduct();
  }

  getTop10Pro() {
    return this.proApi.getTop10Pro();
  }

  // getProductNeverRating( id : number ){
  //    return this.proApi.getProductNeverRating(id) ;
  // }

  getProductSimilar(idCate: number) {
    return this.proApi.getProductSimilar(idCate);
  }
}

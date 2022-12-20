import {Injectable, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})

export class ProductApiService {
   constructor( private http: HttpClient ) {
   }

   getAll(): Observable<any> {
      return this.http.get( ApiConstrant.product ) ;
   }

   getById( id: number) : Observable<any> {
     return this.http.get( `${ApiConstrant.product}/${id}`)
   }

   create( pro: any) : Observable<any> {
      return this.http.post( ApiConstrant.product , pro) ;
   }

   update( pro: any ):Observable<any> {
     return this.http.put( `${ApiConstrant.product}/${pro.id}` , pro );
   }

   // Product Image
   getProductImage( id: number ): Observable<any>{
      return this.http.get( `${ApiConstrant.productImage}/list-proId/${id}` );
   }

//   Tình kiếm pro theo size
   getProBySize( path: any ){
     return this.http.get(`${ApiConstrant.product}/findSize?${path}`)
   }

//   Product new
   getNewProduct() {
     return this.http.get(`${ApiConstrant.product}/getNew`)
   }

//   Lấy 10 sản phẩm bán chạy
  getTop10Pro() {
     return this.http.get(`${ApiConstrant.product}/getTop10Pro`)
  }

//  Lấy những sản phẩm chưa được đánh giá
  getProductNeverRating( id: number ) {
     return this.http.get(`${ApiConstrant.product}/getProductNeverRating?id=${id}`)
  }

  getProductSimilar(idCate: number ){
     return this.http.get(`${ApiConstrant.product}/getProSimilar?idCate=${idCate}`)
  }
}

import { Injectable } from '@angular/core';
import {CategoryApiService} from "./category-api.service";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor( private  cateApi: CategoryApiService ) { }

  getAllCategory() {
    return this.cateApi.getAll() ;
  }
}

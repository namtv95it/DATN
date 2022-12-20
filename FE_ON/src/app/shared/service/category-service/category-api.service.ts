import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class CategoryApiService {

  constructor( private http: HttpClient ) { }

  getAll() {
    return this.http.get(ApiConstrant.category)
  }
}

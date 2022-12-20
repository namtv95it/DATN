import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
  providedIn: 'root'
})
export class UploadCloudinaryService {

  constructor( private http: HttpClient) { }

  upload(files: any) {
    return this.http.post(`${ApiConstrant.cloudinary}/upload`, files);
  }

  delete(publicId: any) {
    return this.http.delete(`${ApiConstrant.cloudinary}/${publicId}`);
  }
}

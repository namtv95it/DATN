import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UploadCloudinaryApiService {

  constructor( private uploadServie: UploadCloudinaryApiService ) { }

  // @ts-ignore
  upload(files: any) {
     return this.uploadServie.upload(files);
  }

  // @ts-ignore
  delete(publicId: any) {
    return this.uploadServie.delete(publicId);
  }
}

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiConstrant} from "../../constants/ApiConstrants.module";

@Injectable({
    providedIn: 'root'
})
export class CustomerApiService {

    constructor(private readonly http: HttpClient) {
    }


    getCustomer(id: number): Observable<any> {
        return this.http.get(`${ApiConstrant.customer}/${id}`);
    }

    updateCustomer(data: any, id: number): Observable<any> {
        return this.http.put(`${ApiConstrant.customer}/${id}`, data);
    }

}

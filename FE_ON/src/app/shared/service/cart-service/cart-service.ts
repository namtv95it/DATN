import {Injectable} from '@angular/core';
import {CartApiService} from "./cart-api.service";
import {BehaviorSubject, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  isReload: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private readonly cartApi: CartApiService,
  ) {
  }

  addToCart(data: any) {
    return this.cartApi.addToCart(data)
    //   .subscribe({
    //   next: (res: any) => {
    //     console.log(res);
    //     this.isReload.next(true);
    //   },
    //   error: (err) => {
    //     console.log(err)
    //     this.isReload.next(false);
    //   }
    // })
  }

  updateCart(data: any) {
    return this.cartApi.updateCart(data)
    // .subscribe({
    //   next: (res) => {
    //     console.log(res);
    //     this.isReload.next(true);
    //   },
    //   error: (err) => {
    //     console.log(err)
    //     this.isReload.next(false);
    //   }
    // })
  }

  deleteCart(id: number) {
    return this.cartApi.deleteCart(id).subscribe({
      next: _ => {
        this.isReload.next(true);
      },
      error: (err) => {
        console.log(err)
        this.isReload.next(false);
      }
    })
  }

  deleteAllByCustomerId(customerId: any) {
    return this.cartApi.deleteAllByCustomerId(customerId).subscribe({
      next: _ => {
        this.isReload.next(true);
      },
      error: (err) => {
        console.log(err)
        this.isReload.next(false);
      }
    })
  }

  findAllByCustomerId(customerId: any) {
    return this.cartApi.findAllByCustomerId(customerId);
  }

}

import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {RatingComponent} from "../rating/rating.component";
import {OrderService} from "../../../../shared/service/order/order.service";
import {OrderDetailService} from "../../../../shared/service/order-detail/order-detail.service";
import {ConfirmDialogComponent} from "../../../../shared/confirm-dialog/confirm-dialog.component";
import {Constants} from "../../../../shared/constants/constants.module";
import {RatingService} from "../../../../shared/service/rating-service/rating.service";
import {StorageService} from "../../../../shared/service/storage.service";
import {ExchangeOrderDialogComponent} from './exchange-order-dialog/exchange-order-dialog.component';

@Component({
  selector: 'app-user-order',
  templateUrl: './user-order.component.html',
  styleUrls: ['./user-order.component.css']
})
export class UserOrderComponent implements OnInit {

  listOrder: any;
  listOrderDetail: any;
  listRating: any[] = [];
  employeeId = 1;

  listStatus = [
    {status: 0, name: 'Chờ xác nhận'},
    {status: 1, name: 'Đã xác nhận'},
    {status: 2, name: 'Đang giao'},
    {status: 3, name: 'Đã giao'},
    {status: 4, name: 'Đã hủy'},
    // { status: 5 , name: 'Trả hàng hoàn tiền'}
  ]

  constructor(private dialog: MatDialog,
              private orderService: OrderService,
              private orderDetailService: OrderDetailService,
              private ratingService: RatingService,
              private storageService: StorageService,
  ) {
  }

  ngOnInit(): void {
    this.findAllOrder();
    this.findAllOrderDetail();
    this.findAllRating();
  }

  checkRating(id: number, idProduct: number) {
    for (let x of this.listRating) {
      if (x.orders.id == id && x.product.id == idProduct) {
        return false;
      }
    }
    return true;
  }

  checkDay(date: any, days: any) {
    const d = new Date(date);
    d.setDate(d.getDate() + days);
    return new Date().getTime() <= d.getTime();
  }

  openExchange(order: any, checkExchange: any) {
    this.dialog.open(ExchangeOrderDialogComponent, {
      disableClose: true,
      width: '90rem',
      data: {
        order,
        checkExchange
      }
    }).afterClosed().subscribe(rs => {
      if (rs ===  Constants.RESULT_CLOSE_DIALOG.SUCCESS) {
        this.findAllOrder();
      }
    })
  }

  findAllRating() {
    this.ratingService.getAllRatingByIdCustome(this.storageService.getIdFromToken()).subscribe(data => {
      this.listRating = data as any[];
    })
  }

  findAllOrder() {
    this.orderService.getAllOrder(this.storageService.getIdFromToken()).subscribe(data => {
      this.listOrder = data;
      console.log(this.listOrder);
    })
  }

  findAllOrderDetail() {
    this.orderDetailService.getAllOrderDetail(this.storageService.getIdFromToken()).subscribe(data => {
      console.log(data);
      return this.listOrderDetail = data;
    })
  }

  OnpenRating(orderId: number, productId: number) {
    this.dialog.open(RatingComponent, {
      width: '30vw',
      disableClose: true,
      hasBackdrop: true,
      autoFocus: false,
      data: {
        order_id: orderId,
        product_id: productId,
        employee_id: this.employeeId
      }
    }).afterClosed().subscribe(value => {
      if (value == Constants.RESULT_CLOSE_DIALOG.SUCCESS) {
        this.findAllOrder();
        this.findAllOrderDetail();
        this.findAllRating();
      }
    })
  }

  openCancel(id: number) {
    this.dialog.open(ConfirmDialogComponent, {
      width: '30vw',
      disableClose: true,
      hasBackdrop: true,
      data: {
        title: 'Xác nhận',
        message: 'Bạn có muốn hủy đơn hàng không?'
      }
    }).afterClosed().subscribe(data => {
      if (data === Constants.RESULT_CLOSE_DIALOG.CONFIRM) {
        this.orderService.updateStatusOrder(4, id).subscribe(
          () => {
            this.findAllOrder();
          }
        );
      }
    })
  }

  checkDate(a: any) {
    let date = new Date();
    date.setDate(date.getDate() - 5)
    if (date <= new Date(a)) {
      return true;
    }
    return false;
  }
}

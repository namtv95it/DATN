import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {OrderService} from "../../../shared/service/order/order.service";
import {StorageService} from "../../../shared/service/storage.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnChanges {

  countAll = 0;
  countCancel = 0;
  listOrder: any[] = [];
  checkColor = 1;
  fullname: any;

  constructor(private orderService: OrderService,
              private storageService: StorageService) {
  }

  ngOnChanges(): void {
    this.countCancel += 1 ;
  }

  getAllOrder(){
    this.orderService.getAllOrder(this.storageService.getIdFromToken()).subscribe( data => {
        this.listOrder =  data as any[] ;
        this.countAll = this.listOrder.length ;
        this.countOrder() ;
    })
  }

  countOrder(){
      for( let x of this.listOrder ){
          if( x.status == 4 ){
              this.countCancel += 1;
          }
      }
  }

  ngOnInit(): void {
    this.getAllOrder() ;
    this.fullname = this.storageService.getFullNameFromToken() ;
  }

  // cancelCount( id: number ){
  //   this.countCancel += id ;
  // }
}

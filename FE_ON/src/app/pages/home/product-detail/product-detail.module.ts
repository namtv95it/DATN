import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductDetailComponent} from "./product-detail.component";
import {ProductDetailRoutingModule} from "./product-detail-routing.module";
import {FormsModule} from "@angular/forms";
import {SlickCarouselModule} from "ngx-slick-carousel";
import {HomeModule} from "../home/home.module";
import {UserOrderModule} from "../profile/user-order/user-order.module";
import {HomeRountingModule} from "../home/home-rounting.module";
import {SharedModule} from "../../../shared/shared.module";
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    ProductDetailComponent,
  ],
  imports: [
    CommonModule,
    ProductDetailRoutingModule,
    FormsModule,
    SlickCarouselModule,
    UserOrderModule,
    SharedModule,
    MatDialogModule,

  ]
})
export class ProductDetailModule {
}

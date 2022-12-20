import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductDetailComponent} from "./product-detail.component";
import {ProductDetailRoutingModule} from "./product-detail-routing.module";
import {FormsModule} from "@angular/forms";
import {SlickCarouselModule} from "ngx-slick-carousel";
import {HomeModule} from "../home/home.module";
import {UserOrderModule} from "../profile/user-order/user-order.module";

@NgModule({
  declarations: [
    ProductDetailComponent
  ],
    imports: [
        CommonModule,
        ProductDetailRoutingModule,
        FormsModule,
        SlickCarouselModule,
        HomeModule,
        UserOrderModule,
    ]
})
export class ProductDetailModule {
}

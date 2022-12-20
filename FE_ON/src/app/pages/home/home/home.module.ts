import { NgModule } from '@angular/core';
import { CommonModule  } from '@angular/common';
import {HomeRountingModule} from "./home-rounting.module";
import {HomeComponent} from "./home.component";
import {StarsComponent} from "../../../shared/stars/stars.component";
import {SlickCarouselModule} from "ngx-slick-carousel";
import {MatDialogModule} from "@angular/material/dialog";
import {ProductViewComponent} from "./product-view/product-view.component";

@NgModule({
    declarations: [
        HomeComponent,
        StarsComponent,
    ],
    exports: [
        StarsComponent
    ],
    imports: [
        CommonModule,
        HomeRountingModule,
        SlickCarouselModule ,
      MatDialogModule
    ]
})
export class HomeModule {

}

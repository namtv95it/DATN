import { NgModule } from '@angular/core';
import { CommonModule  } from '@angular/common';
import {HomeRountingModule} from "./home-rounting.module";
import {HomeComponent} from "./home.component";
import {SlickCarouselModule} from "ngx-slick-carousel";
import {MatDialogModule} from "@angular/material/dialog";
import {SharedModule} from "../../../shared/shared.module";
import { GuideSizeComponent } from './product-view/guide-size/guide-size.component';

@NgModule({
    declarations: [
        HomeComponent,
        GuideSizeComponent
    ],
  imports: [
    CommonModule,
    HomeRountingModule,
    SlickCarouselModule,
    MatDialogModule,
    SharedModule
  ]
})
export class HomeModule {

}

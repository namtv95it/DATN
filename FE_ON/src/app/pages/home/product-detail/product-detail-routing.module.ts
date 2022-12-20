import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {ProductDetailComponent} from "./product-detail.component";


const router: Routes = [
  {
    path: ':id' ,
    component: ProductDetailComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class ProductDetailRoutingModule { }

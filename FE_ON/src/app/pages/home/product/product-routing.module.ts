import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ProductComponent} from "./product.component";

const router: Routes = [
  {
    path: '' ,
    component: ProductComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }

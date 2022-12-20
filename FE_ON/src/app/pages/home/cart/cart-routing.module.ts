import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {CartComponent} from "./cart.component";

const router: Routes = [
  {path: "" , component: CartComponent }
]

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class CartRoutingModule { }

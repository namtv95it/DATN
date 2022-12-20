import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {UserOrderComponent} from "./user-order.component";

const router: Routes = [
  {
    path: '' ,
    component: UserOrderComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class UserOrderRoutingModule { }

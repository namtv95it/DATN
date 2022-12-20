import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {UserOrderComponent} from "./user-order/user-order.component";
import {ProfileComponent} from "./profile.component";
import {router_user} from "../../../shared/routers/router-user.module";

const router: Routes = [
  {
    path: '' ,
    redirectTo: 'user-profile' ,
    pathMatch: 'full'
  },
  {
    path: '' ,
    component: ProfileComponent ,
    children: router_user
  }
]

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }

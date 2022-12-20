import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {UserProflleComponent} from "./user-proflle.component";

const router: Routes = [
  { path: '' , component: UserProflleComponent }
]


@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class UserProfileRoutingModule { }

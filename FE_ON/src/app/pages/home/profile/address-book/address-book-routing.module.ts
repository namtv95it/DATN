import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {AddressBookComponent} from "./address-book.component";

const router: Routes = [
  {path: '' , component: AddressBookComponent }
]

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class AddressBookRoutingModule { }

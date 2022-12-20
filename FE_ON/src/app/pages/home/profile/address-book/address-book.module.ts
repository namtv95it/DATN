import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AddressBookRoutingModule} from "./address-book-routing.module";
import {AddressBookComponent} from "./address-book.component";
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatOptionModule} from "@angular/material/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatTooltipModule} from "@angular/material/tooltip";

@NgModule({
  declarations: [
    AddressBookComponent
  ],
  imports: [
    CommonModule ,
    AddressBookRoutingModule,
    MatDialogModule,
    ReactiveFormsModule,
    FormsModule,
    MatOptionModule,
    MatFormFieldModule,
    MatSelectModule,
    MatTooltipModule
  ]
})
export class AddressBookModule { }

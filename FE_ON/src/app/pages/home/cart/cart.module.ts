import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CartRoutingModule} from "./cart-routing.module";
import {CartComponent} from "./cart.component";
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { EditAddressComponent } from './edit-address/edit-address.component';
import { AddNewAddressComponent } from './add-new-address/add-new-address.component';
import {MatOptionModule} from "@angular/material/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatTooltipModule} from "@angular/material/tooltip";

@NgModule({
  declarations: [
    CartComponent,
    EditAddressComponent,
    AddNewAddressComponent
  ],
  imports: [
    CommonModule,
    CartRoutingModule,
    MatDialogModule,
    ReactiveFormsModule,
    FormsModule,
    MatOptionModule,
    MatFormFieldModule,
    MatSelectModule,
    MatTooltipModule
  ]
})

export class CartModule { }

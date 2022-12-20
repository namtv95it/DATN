import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductRoutingModule} from "./product-routing.module";
import {ProductComponent} from "./product.component";
import {HomeModule} from "../home/home.module";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

@NgModule({
  declarations: [
     ProductComponent,
  ],
  imports: [
    CommonModule,
    ProductRoutingModule,
    HomeModule,
    FormsModule,
    ReactiveFormsModule ,
    MatFormFieldModule ,
    MatInputModule
  ]
})
export class ProductModule { }

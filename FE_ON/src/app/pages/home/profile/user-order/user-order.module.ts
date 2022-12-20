import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserOrderRoutingModule} from "./user-order-routing.module";
import {UserOrderComponent} from "./user-order.component";
import {MatTabsModule} from "@angular/material/tabs";
import {MatFormFieldModule} from "@angular/material/form-field";
import { ExchangeOrderDialogComponent } from './exchange-order-dialog/exchange-order-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import {MatCardModule} from "@angular/material/card";
import {MatStepperModule} from "@angular/material/stepper";
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxDropzoneModule} from "ngx-dropzone";
import {NumberOnlyDirective} from "../../../../shared/directive/number-only.directive";

@NgModule({
    declarations: [
        UserOrderComponent,
        ExchangeOrderDialogComponent,
        NumberOnlyDirective
    ],
    exports: [
        NumberOnlyDirective
    ],
    imports: [
        CommonModule,
        UserOrderRoutingModule,
        MatTabsModule,
        MatFormFieldModule,
        MatDialogModule,
        MatCardModule,
        MatStepperModule,
        MatTableModule,
        MatCheckboxModule,
        MatInputModule,
        ReactiveFormsModule,
        NgxDropzoneModule,
        FormsModule
    ]
})
export class UserOrderModule { }

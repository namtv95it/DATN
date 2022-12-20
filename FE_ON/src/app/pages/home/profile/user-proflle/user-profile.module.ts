import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserProflleComponent} from "./user-proflle.component";
import {UserProfileRoutingModule} from "./user-profile-routing.module";
import { UserProfileFormComponent } from './user-profile-form/user-profile-form.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import { UserProfileImageComponent } from './user-profile-image/user-profile-image.component';
import {NgxDropzoneModule} from "ngx-dropzone";
import { UserProfileChangePasswordComponent } from './user-profile-change-password/user-profile-change-password.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [
     UserProflleComponent,
     UserProfileFormComponent,
     UserProfileImageComponent,
     UserProfileChangePasswordComponent
  ],
  imports: [
    CommonModule,
    UserProfileRoutingModule,
    MatFormFieldModule,
    MatInputModule,
    NgxDropzoneModule,
    ReactiveFormsModule,
    MatButtonModule,
  ]
})
export class UserProfileModule { }


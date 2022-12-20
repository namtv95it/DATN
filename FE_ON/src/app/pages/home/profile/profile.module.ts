import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileRoutingModule} from "./profile-routing.module";
import {ProfileComponent} from "./profile.component";
import {SignupComponent} from './signup/signup.component';
import {ForgotPasswordComponent} from './forgot-password/forgot-password.component';
import {RatingComponent} from './rating/rating.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatRadioModule} from "@angular/material/radio";
import {NgxDropzoneModule} from "ngx-dropzone";
import {NgxStarsModule} from "ngx-stars";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SigninComponent} from "./signin/signin.component";
import {MatFormFieldModule} from "@angular/material/form-field";

@NgModule({
  declarations: [
    ProfileComponent,
    SignupComponent,
    SigninComponent ,
    ForgotPasswordComponent,
    RatingComponent,
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatRadioModule,
    NgxDropzoneModule,
    NgxStarsModule,
    MatFormFieldModule,
  ]
})
export class ProfileModule {
}

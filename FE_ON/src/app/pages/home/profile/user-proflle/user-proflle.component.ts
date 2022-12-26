import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {UserProfileImageComponent} from "./user-profile-image/user-profile-image.component";
import {StorageService} from "../../../../shared/service/storage.service";
import {CustomerService} from "../../../../shared/service/customer/customer.service";
import {UserProfileFormComponent} from "./user-profile-form/user-profile-form.component";
import {
  UserProfileChangePasswordComponent
} from "./user-profile-change-password/user-profile-change-password.component";
import {Constants} from "../../../../shared/constants/constants.module";

@Component({
  selector: 'app-user-proflle',
  templateUrl: './user-proflle.component.html',
  styleUrls: ['./user-proflle.component.css']
})
export class UserProflleComponent implements OnInit {

  customer: any;
  avtUrl: any;

  constructor(private dialog: MatDialog,
              private readonly storageService: StorageService,
              private readonly customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.getCustomer();
  }

  getCustomer() {
    this.customerService.getCustomer(this.storageService.getIdFromToken()).subscribe(res => this.customer = res);
  }

  OpenDialogImage() {
    this.dialog.open(UserProfileImageComponent, {
      width: '25vw',
      hasBackdrop: true,
      disableClose: true,
    }).afterClosed().subscribe(rs => {
      if (rs?.type === Constants.RESULT_CLOSE_DIALOG.SUCCESS) {
        this.avtUrl = rs.url;
      }
    })
  }

  openDialogUpdate() {
    this.dialog.open(UserProfileFormComponent, {
      width: '25vw',
      hasBackdrop: true,
      disableClose: true,
      data: {
        customer: this.customer
      }
    }).afterClosed().subscribe(rs => {
      if (rs === Constants.RESULT_CLOSE_DIALOG.SUCCESS) {
        this.getCustomer();
      }
    })
  }

  openDialogChangePassword() {
    this.dialog.open(UserProfileChangePasswordComponent, {
      width: '25vw',
      hasBackdrop: true,
      disableClose: true,
    })
  }
}

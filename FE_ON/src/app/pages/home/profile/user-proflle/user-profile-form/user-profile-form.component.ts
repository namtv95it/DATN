import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import {Constants} from "../../../../../shared/constants/constants.module";
import {CustomerService} from "../../../../../shared/service/customer/customer.service";
import { ConfirmDialogComponent } from '../../../../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-user-profile-form',
  templateUrl: './user-profile-form.component.html',
  styleUrls: ['./user-profile-form.component.css']
})
export class UserProfileFormComponent implements OnInit {


  formGroup = this.fb.group({
    id: [''],
    fullname: ['', [Validators.required]],
    photo: ['', []],
    email: ['', [Validators.required]],
    password: ['', [Validators.required]],
    phone: ['', [Validators.required]],
    birthDate: ['', [Validators.required]],
    status: [1]
  })

  constructor(private readonly fb: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public matDataDialog: any,
              private readonly matDialog: MatDialogRef<UserProfileFormComponent>,
              private readonly dialog: MatDialog,
              private readonly customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.formGroup.patchValue(this.matDataDialog.customer);
    this.formGroup.patchValue({birthDate: this.formatDate(this.matDataDialog.customer.birthDate)})
  }

  save() {
    this.formGroup.markAllAsTouched();
    if (this.formGroup.invalid) {
      return;
    }
    this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      hasBackdrop: true,
      width: "25vw",
      data: {
        message: 'Bạn có muốn cập nhật thông tin?'
      }
    }).afterClosed().subscribe((result:any) => {
      if (result === Constants.RESULT_CLOSE_DIALOG.CONFIRM) {
        this.customerService.updateCustomer(this.formGroup.getRawValue(), this.matDataDialog.customer.id);
        this.matDialog.close(Constants.RESULT_CLOSE_DIALOG.SUCCESS);
      }
    })

  }

  onDismiss() {
    this.matDialog.close(Constants.RESULT_CLOSE_DIALOG.CLOSE);
  }

  private formatDate(date: any) {
    const d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
  }
}

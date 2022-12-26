import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import {Constants} from "../../../../../shared/constants/constants.module";
import {AuthService} from "../../../../../shared/service/auth/auth.service";
import {StorageService} from "../../../../../shared/service/storage.service";
import {ToastrService} from "ngx-toastr";
import { ConfirmDialogComponent } from '../../../../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-user-profile-change-password',
  templateUrl: './user-profile-change-password.component.html',
  styleUrls: ['./user-profile-change-password.component.css']
})
export class UserProfileChangePasswordComponent implements OnInit {

  formGroup = this.fb.group({
    newPassword: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
  })

  constructor(private readonly fb: FormBuilder,
              private dialogRef: MatDialogRef<UserProfileChangePasswordComponent>,
              private readonly authService: AuthService,
              private readonly storageService: StorageService,
              private readonly matDialog: MatDialog,
              private readonly toastService: ToastrService) {
  }

  ngOnInit(): void {
  }

  onDismiss() {
    this.dialogRef.close(Constants.RESULT_CLOSE_DIALOG.CLOSE);
  }

  save() {
    this.formGroup.markAllAsTouched();
    if (this.formGroup.invalid) return;

    const data = {
      customerId: this.storageService.getIdFromToken(),
      newPassword: this.formGroup.getRawValue().newPassword
    }
    this.matDialog.open(ConfirmDialogComponent, {
      disableClose: true,
      hasBackdrop: true,
      width: "25vw",
      data: {
        message: 'Bạn có muốn đổi mật khẩu?'
      }
    }).afterClosed().subscribe((result:any) => {
      if (result === Constants.RESULT_CLOSE_DIALOG.CONFIRM) {
        this.authService.changePassword(data).subscribe(res => {
          if (res) {
            this.toastService.success("Đổi mật khẩu thành công !");
          } else {
            this.toastService.success("Đổi mật khẩu thất bại !");
          }
        })
        this.dialogRef.close(Constants.RESULT_CLOSE_DIALOG.SUCCESS);
      }
    })

  }

}

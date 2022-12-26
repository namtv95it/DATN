import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../../../shared/service/auth/auth.service";
import {DOCUMENT} from "@angular/common";
import {StorageService} from "../../../../shared/service/storage.service";
import { Constants } from '../../../../shared/constants/constants.module';
import { ConfirmDialogComponent } from '../../../../shared/confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {finalize} from "rxjs";


@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  formGroup = this.fb.group({
    email: ['', Validators.required]
  })

  resetPasswordForm = this.fb.group({
    customerId: [''],
    newPassword: ['']
  })

  domain: any;
  resetPasswordToken: any;
  customer: any;
  confirmNewPassword: any = '';

  isShowEmailForm: boolean = true;
  isShowMessage: boolean = false;
  isShowResetPasswordForm: boolean = false;

  constructor(private readonly fb: FormBuilder,
              private readonly toastService: ToastrService,
              private readonly authService: AuthService,
              private readonly route: ActivatedRoute,
              private readonly router: Router,
              private readonly storageService: StorageService,
              private readonly matDialog: MatDialog,
              @Inject(DOCUMENT) private document: any) {
  }


  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      void this.router.navigate(['/home']);
      return;
    }
    this.route.queryParams.subscribe(params => {
      this.resetPasswordToken = params['token'];
    });
    if (this.resetPasswordToken) {
      this.authService.getCustomerByPasswordToken(this.resetPasswordToken).subscribe({
        next: (res) => {
          this.customer = res;
          console.log(this.customer);
          this.isShowResetPasswordForm = true;
          this.isShowEmailForm = false;
          this.isShowMessage = false;
        },
        error: (err) => {
          if (err.error.code == 'NOT_FOUND') {
            this.isShowResetPasswordForm = false;
            this.isShowMessage = false;
            this.toastService.error("Reset password token không hợp lệ !")
          }
        }
      })
    }
  }

  check: boolean = false;

  onSendEmail() {
    const email = this.formGroup.getRawValue().email;
    this.domain = 'http://' + this.document.location.hostname + ':' + this.document.location.port;
    if (email?.trim() == '') {
      this.toastService.warning("Vui lòng nhập email !")
      return;
    }
    if (!email?.trim().match('^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$')) {
      this.toastService.warning("Email sai định dạng !")
      return;
    }
    this.check = true;
    this.authService.sendEmailForgotPassword(email?.trim(), this.domain)
      .pipe(finalize(() => {
        this.check = false;
      }))
      .subscribe({
        next: (res) => {
          if (res) {
            this.isShowEmailForm = false;
            this.isShowMessage = true;
          } else {
            this.toastService.error("Gửi liên kết thât bại vui lòng thử lại sau !")
            this.isShowEmailForm = true;
          }
        },
        error: (err) => {
          if (err.error.code == 'NOT_FOUND') {
            this.toastService.error("Email không tồn tại !")
          }
        }
      })
  }

  onChangePassword() {
    const newPassword = this.resetPasswordForm.getRawValue().newPassword;

    if ('' == newPassword?.trim() || '' == this.confirmNewPassword.trim()) {
      this.toastService.warning("Vui lòng nhập đầy đủ thông tin !");
      return;
    }

    if (newPassword!.length < 6) {
      this.toastService.warning("Mật khẩu phải lớn hơn 6 ký tự!")
      return;
    }

    if (newPassword != this.confirmNewPassword) {
      this.toastService.warning("Xác nhận mật khẩu không trùng khớp !")
      return;
    }

    const data = {
      customerId: this.customer.id,
      newPassword: newPassword
    }
    this.authService.changePassword(data).subscribe({
      next: (res) => {
        if (res) {
          this.toastService.success("Thay đổi mật khẩu thành công !");
          void this.router.navigate(['/sign-in']);
        }
      },
      error: (err) => {
        this.toastService.error("Thay đổi mật khẩu thất bại !");
      }
    })
  }
}

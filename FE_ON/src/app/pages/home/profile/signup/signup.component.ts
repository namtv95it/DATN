import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "../../../../shared/service/auth/auth.service";
import {StorageService} from "../../../../shared/service/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  formGroup = this.fb.group({
    fullname: ['', Validators.required],
    email: ['', Validators.required],
    phone: ['', Validators.required],
    birthDate: ['', Validators.required],
    password: ['', Validators.required],
  });
  confirmPassword: any = '';

  constructor(private readonly fb: FormBuilder,
              private readonly toastService: ToastrService,
              private readonly authService: AuthService,
              private readonly storageService: StorageService,
              private readonly router: Router) {
  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      void this.router.navigate(["/home"]);
    }
  }

  onSignUp() {
    const fullname = this.formGroup.getRawValue().fullname;
    const email = this.formGroup.getRawValue().email;
    const phone = this.formGroup.getRawValue().phone;
    const birthDate = this.formGroup.getRawValue().birthDate;
    const password = this.formGroup.getRawValue().password;
    if (fullname?.trim() == '' || email?.trim() == '' || birthDate == '' || password?.trim() == '' || '' == this.confirmPassword.trim()) {
      this.toastService.warning("Vui lòng nhập đầy đủ thông tin !");
      return;
    }
    if (!phone?.trim().match("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
      this.toastService.warning("Số điện thoại không đúng định dạng !")
      return;
    }
    if (!email?.trim().match('^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$')) {
      this.toastService.warning("Email sai định dạng !")
      return;
    }

    if (password!.length < 6) {
      this.toastService.warning("Mật khẩu phải lớn hơn 6 ký tự!")
      return;
    }

    if (password != this.confirmPassword) {
      this.toastService.warning("Xác nhận mật khẩu không trùng khớp !")
      return;
    }

    const data = {
      fullname: fullname?.trim(),
      email: email?.trim(),
      phone: phone?.trim(),
      birthDate,
      password: password?.trim()
    }

    console.log(data);
    this.authService.register(data).subscribe({
      next: (res) => {
        console.log(res);
        this.toastService.success("Đăng ký thành công !");
        void this.router.navigate(["/sign-in"]);
      },
      error: (err) => {
        if (err.error.code === 'UNIQUE_FIELD') {
          this.toastService.error(err.error.message);
          return;
        }
        this.toastService.error("Đăng ký thất bại !")
      }
    })

  }

}

import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {CartService} from "../../../../shared/service/cart-service/cart-service";
import {AuthService} from "../../../../shared/service/auth/auth.service";
import {StorageService} from "../../../../shared/service/storage.service";

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  formGroup = this.fb.group({
    email: [''],
    password: ['']
  })

  redirectUrl!: string;

  constructor(private readonly fb: FormBuilder,
              private readonly authService: AuthService,
              private readonly toastService: ToastrService,
              private readonly storageService: StorageService,
              private readonly router: Router,
              private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      void this.router.navigate(["/home"]);
    }
  }

  onLogin() {
    const email = this.formGroup.getRawValue().email;
    const password = this.formGroup.getRawValue().password;
    if (email?.trim() === '' || password?.trim() === '') {
      this.toastService.warning("Vui lòng nhập đầy đủ thông tin !")
      return;
    }
    //
    if (!email?.trim().match('^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$')) {
      this.toastService.warning("Email sai định dạng !")
      return;
    }

    const data = {
      email: email?.trim(),
      password: password?.trim()
    }

    this.authService.login(data).subscribe({
      next: (res) => {
        this.storageService.saveUserToken(res);
        let param = this.route.snapshot.queryParams;
        if (param['redirectUrl']) {
          this.redirectUrl = param['redirectUrl'];
        }
        if (this.redirectUrl) {
          this.router.navigateByUrl(this.redirectUrl)
            .then(() => this.storageService.reloadPage())
            .catch(() => this.router.navigate(['/home']))
        } else {
          this.router.navigate(['/home']).then(() => this.storageService.reloadPage())
        }
      },
      error: (err) => {
        if (err.error.code == 'NOT_FOUND') {
          this.toastService.error(err.error.message);
          return;
        }
        if (err.error.code == 'LOGIN_INVALID') {
          this.toastService.error(err.error.message);
          return;
        }
        if (err.error.code == 'INACTIVE') {
          this.toastService.error(err.error.message);
          return;
        }
        this.toastService.error('Sai thông tin tài khoản hoặc mật khẩu!');
      }
    })
  }

}

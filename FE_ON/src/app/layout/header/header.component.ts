import {Component, OnInit} from '@angular/core';
import {StorageService} from "../../shared/service/storage.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../shared/confirm-dialog/confirm-dialog.component";
import {Constants} from "../../shared/constants/constants.module";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public readonly storageService: StorageService,
              private readonly router: Router,
              private readonly toastService: ToastrService,
              private readonly dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  logout() {
    this.dialog.open(ConfirmDialogComponent,{
      disableClose: true,
      hasBackdrop: true,
      width: "25vw",
      data: {
        message: 'Bạn có đăng xuất không?'
      }
    }).afterClosed().subscribe((result) => {
      if (result === Constants.RESULT_CLOSE_DIALOG.CONFIRM) {
        this.storageService.clearToken();
        this.toastService.success("Đăng xuất thành công !")
        this.router.navigate(["/home"]).then(() => this.storageService.reloadPage())
      }
    })
  }
}

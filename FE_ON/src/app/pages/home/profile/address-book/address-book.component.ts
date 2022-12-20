import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {AddNewAddressComponent} from "../../cart/add-new-address/add-new-address.component";
import {AddressService} from "../../../../shared/service/address/address.service";
import {StorageService} from "../../../../shared/service/storage.service";
import {Constants} from "../../../../shared/constants/constants.module";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-address-book',
  templateUrl: './address-book.component.html',
  styleUrls: ['./address-book.component.css']
})
export class AddressBookComponent implements OnInit {

  addressInCustomer: any[] = [];
  readonly TYPE_DIALOG = Constants.TYPE_DIALOG;

  constructor(private readonly matDialog: MatDialog,
              private readonly addressService: AddressService,
              private readonly storageService: StorageService,
              private readonly toastService: ToastrService) {
  }

  ngOnInit(): void {
    this.findAddressByCustomerId();
  }

  openForm(type: any, row?: any) {
    let listAddress = this.addressInCustomer;
    this.matDialog.open(AddNewAddressComponent, {
      width: '40vw',
      height: '21w',
      disableClose: true,
      hasBackdrop: true,
      data: {
        row,
        type,
        listAddress
      }
    }).afterClosed().subscribe(result => {
      if (result === Constants.RESULT_CLOSE_DIALOG.SUCCESS) {
        this.findAddressByCustomerId();
      }
    })
  }

  findAddressByCustomerId() {
    this.addressService.findAddressByCustomerId(this.storageService.getIdFromToken()).subscribe((res: any) => {
      this.addressInCustomer = res as any[];
      console.log(this.addressInCustomer)
    })
  }

  onDelete(id: number, status: number) {
    if (status == 1) {
      this.toastService.warning("Bạn không thể xoá địa chỉ mặc định !");
      return;
    }
    this.addressService.deleteAddress(id).subscribe({
      next: _ => {
        this.toastService.success("Xoá địa chỉ thành công !")
        this.findAddressByCustomerId();
      },
      error: (err) => {
        console.log(err);
        this.toastService.error("Xoá địa chỉ thất bại !")
      }
    })
  }
}

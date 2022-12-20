import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Constants} from "../../../../shared/constants/constants.module";
import {AddNewAddressComponent} from "../add-new-address/add-new-address.component";
import {AddressService} from "../../../../shared/service/address/address.service";
import {CustomerService} from "../../../../shared/service/customer/customer.service";
import {NgForm} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {StorageService} from "../../../../shared/service/storage.service";

@Component({
  selector: 'app-edit-address',
  templateUrl: './edit-address.component.html',
  styleUrls: ['./edit-address.component.css']
})
export class EditAddressComponent implements OnInit {

  addressInCustomer: any[] = [];
  customer: any;
  idAddress: any;
  readonly TYPE_DIALOG = Constants.TYPE_DIALOG;

  constructor(private readonly matDialogRef: MatDialogRef<EditAddressComponent>,
              private readonly matDialog: MatDialog,
              private readonly addressService: AddressService,
              private readonly customerService: CustomerService,
              private readonly toastService: ToastrService,
              @Inject(MAT_DIALOG_DATA) public dataDialog: any,
              private readonly storageService: StorageService) {
  }

  idTest: any;

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.findAddressByCustomerId(this.storageService.getIdFromToken());
    }
    if (this.dataDialog && this.idAddress) {
      this.idAddress = this.dataDialog.id;
      this.idTest = this.dataDialog.id;
    }
  }

  openAddNewAddress(type?: any, row?: any) {
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
        this.findAddressByCustomerId(this.storageService.getIdFromToken());
      }
    })
  }

  findAddressByCustomerId(customerId: any) {
    this.addressService.findAddressByCustomerId(customerId).subscribe((res: any) => {
      this.addressInCustomer = res as any[];
    })
  }

  onClickRadio(id: any) {
    this.idAddress = id;
  }

  onSaveAddress() {
    this.matDialogRef.close(this.idAddress);
  }

  onClose() {
      this.matDialogRef.close(this.idAddress); //Not clean
  }

  onDelete(id: number, status: number) {
    if (status == 1) {
      this.toastService.warning("Bạn không thể xoá địa chỉ mặc định !");
      return;
    }
    this.addressService.deleteAddress(id).subscribe({
      next: _ => {
        this.toastService.success("Xoá địa chỉ thành công !")
        this.findAddressByCustomerId(this.storageService.getIdFromToken());
        if (this.addressInCustomer.length > 0) {
          this.idAddress = this.addressInCustomer.filter(a => a.status === 1)[0].id;
        }
      },
      error: (err) => {
        this.toastService.error("Xoá địa chỉ thất bại !")
      }
    })
  }
}

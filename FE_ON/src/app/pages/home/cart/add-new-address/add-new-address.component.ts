import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Constants} from "../../../../shared/constants/constants.module";
import {FormBuilder} from "@angular/forms";
import {AddressService} from "../../../../shared/service/address/address.service";
import {ToastrService} from "ngx-toastr";
import {BehaviorSubject} from "rxjs";
import {StorageService} from "../../../../shared/service/storage.service";

@Component({
  selector: 'app-add-new-address',
  templateUrl: './add-new-address.component.html',
  styleUrls: ['./add-new-address.component.css']
})
export class AddNewAddressComponent implements OnInit {
  title: string = '';

  provinces!: any[];
  districts!: any[];
  wards!: any[];
  defaultAddress: number = 0;
  provinceName: any;
  districtName: any;
  wardName: any;

  formGroup = this.fb.group({
    id: [null],
    fullname: [''],
    phone: [''],
    provinceId: [-1],
    provinceName: [''],
    districtId: [-1],
    districtName: [''],
    wardId: [-1],
    wardName: [''],
    other: [''],
    customer: {
      id: this.storageService.getIdFromToken()
    },
    status: [0]
  })

  constructor(private readonly matDialogRef: MatDialogRef<AddNewAddressComponent>,
              @Inject(MAT_DIALOG_DATA) public matDataDialog: any,
              private readonly fb: FormBuilder,
              private readonly addressService: AddressService,
              private readonly toastService: ToastrService,
              private readonly storageService: StorageService) {
  }

  ngOnInit(): void {
    if (this.matDataDialog.type === Constants.TYPE_DIALOG.NEW) {
      this.title = "Thêm mới địa chỉ";
    } else {
      this.title = "Cập nhật địa chỉ";
      this.formGroup.patchValue(this.matDataDialog.row);
    }
    this.getProvince();
  }

  getProvince() {
    this.addressService.getProvince().subscribe((res: any) => {
      this.provinces = res.data;
    })
  }

  getDistrict(provinceId: any, provinceName: any) {
    this.addressService.getDistrict(provinceId).subscribe((res: any) => {
      this.districts = res.data;
    })
    this.provinceName = provinceName;
  }

  getWard(districtId: any, districtName: any) {
    this.addressService.getWard(districtId).subscribe((res: any) => {
      this.wards = res.data;
    })
    this.districtName = districtName;
  }

  resetDistrictAndWard() {
    this.formGroup.patchValue({districtId: -1});
    this.formGroup.patchValue({wardId: -1});
    this.districts = [];
    this.wards = [];
  }

  resetWard() {
    this.formGroup.patchValue({wardId: -1});
    this.wards = [];
  }

  getWardName(wardName: any) {
    this.wardName = wardName;
  }

  onClose() {
    this.matDialogRef.close(Constants.RESULT_CLOSE_DIALOG.CLOSE);
  }

  onSave() {
    if (this.formGroup.getRawValue().provinceId === -1 ||
    this.formGroup.getRawValue().districtId === -1 ||
    this.formGroup.getRawValue().wardId === -1 ||
    this.formGroup.getRawValue().fullname === '' ||
    this.formGroup.getRawValue().phone === '' ||
    this.formGroup.getRawValue().other === ''
    ) {
      this.toastService.warning("Vui lòng chọn đầy đủ thông tin !")
      return;
    }
    // @ts-ignore
    if (!this.formGroup.getRawValue().phone.match("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
      this.toastService.warning("Số điện thoại không đúng định dạng !")
      return;
    }

    if (this.matDataDialog.type === Constants.TYPE_DIALOG.NEW) {
      this.formGroup.patchValue({
        wardName: this.wardName,
        districtName: this.districtName,
        provinceName: this.provinceName
      })
      if (this.matDataDialog.listAddress.length == 0) { //Đặt địa chỉ đầu tiên làm mặc định
        this.formGroup.patchValue({status: 1});
      }
      this.addressService.createAddress(this.formGroup.getRawValue())
        .subscribe((data: any) => {
          if (data.id) {
            this.toastService.success("Thêm mới địa chỉ thành công !")
          } else {
            this.toastService.error("Thêm mới địa chỉ thất bại !")
          }
        })
    } else {
      if (this.defaultAddress === 1) {
        this.addressService.findAddressByStatus(this.storageService.getIdFromToken()).subscribe((data: any) => {
          if (data.id != this.matDataDialog.row.id) {
            data.status = 0;
            this.addressService.updateAddress(data.id, data).subscribe();
          }
        })
        this.formGroup.patchValue({status: 1});
      } else {
        this.formGroup.patchValue({status: this.matDataDialog.row.status});
      }
      this.addressService.updateAddress(this.matDataDialog.row.id, this.formGroup.getRawValue())
        .subscribe((data: any) => {
          if (data.id) {
            this.toastService.success("Cập nhật địa chỉ thành công !")
          } else {
            this.toastService.error("Cập nhật địa chỉ thất bại !")
          }
        })
    }
    this.matDialogRef.close(Constants.RESULT_CLOSE_DIALOG.SUCCESS);
  }

  onClickDefaultAddress(event: any) {
    if (event.target.value == 'on') {
      this.defaultAddress = 1;
      event.target.value = 'off'
    } else {
      event.target.value = 'on'
    }
  }

}

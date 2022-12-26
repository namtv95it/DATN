import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../../shared/confirm-dialog/confirm-dialog.component";
import {Constants} from "../../../shared/constants/constants.module";
import {CartService} from "../../../shared/service/cart-service/cart-service";
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {EditAddressComponent} from "./edit-address/edit-address.component";
import {AddressService} from "../../../shared/service/address/address.service";
import {CustomerService} from "../../../shared/service/customer/customer.service";
import {OrderService} from "../../../shared/service/order/order.service";
import {OrderDetailService} from "../../../shared/service/order-detail/order-detail.service";
import {Router} from "@angular/router";
import {StorageService} from "../../../shared/service/storage.service";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  carts: any[] = [];
  subTotal: number = 0;
  quantity: number = 0;
  customer: any;
  weight: number = 0;

  provinces!: any[];
  districts!: any[];
  wards!: any[];
  address: any;

  provinceName: any;
  districtName: any;
  wardName: any;

  districtId: any;
  serviceId: any;
  shippingTotal: number = 0;

  formGroup = this.fb.group({
    province: [-1],
    district: [-1],
    ward: [-1],
    shipName: [''],
    shipPhone: [''],
    other: [''],
    note: ['']
  })
  defaultInfoModel: boolean = false;

  constructor(private readonly cartService: CartService,
              private readonly matDialog: MatDialog,
              private readonly fb: FormBuilder,
              private readonly toastService: ToastrService,
              private readonly addressService: AddressService,
              private readonly customerService: CustomerService,
              private readonly orderService: OrderService,
              private readonly orderDetailService: OrderDetailService,
              private readonly route: Router,
              private readonly storageService: StorageService) {

  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.findAllByCustomerId();
      this.findAddressByStatus(this.storageService.getIdFromToken());
    }
    this.getProvince();
  }

  findAllByCustomerId() {
    return this.cartService.findAllByCustomerId(this.storageService.getIdFromToken()).subscribe(res => {
      this.carts = res as any[];
      if (this.carts.length > 0) {
        this.subTotal = this.carts
          .map(c => {
            if (c.productsDetail.product.discount !== 0) {
              return (c.productsDetail.product.price - (c.productsDetail.product.price * c.productsDetail.product.discount/100)) * c.quantity;
            } else {
              return c.productsDetail.product.price * c.quantity;
            }
          })
          .reduce((value, total) => value + total, 0);


        this.weight = this.carts
          .map(c => c.productsDetail.product.weight * c.quantity)
          .reduce((value, total) => value + total, 0);

      } else {
        this.subTotal = 0;
        this.weight = 0;
      }
    })
  }

  //Open dialog xoá 1 đơn hàng
  onDelete(id: number) {
    this.matDialog.open(ConfirmDialogComponent, {
      disableClose: true,
      hasBackdrop: true,
      data: {
        message: 'Bạn có muốn xoá sản phẩm này khỏi giỏ hàng?'
      }
    }).afterClosed().subscribe(result => {
      if (result === Constants.RESULT_CLOSE_DIALOG.CONFIRM) {
        this.cartService.deleteCart(id);
        this.cartService.isReload.subscribe((result) => {
          if (result) {
            this.findAllByCustomerId();
            this.cartService.isReload.next(false);
          }
        })
      }
    })
  }

  //Dialog xoá tất cả giỏ hàng
  onOpenDialogDeleteAll() {
    this.matDialog.open(ConfirmDialogComponent, {
      disableClose: true,
      hasBackdrop: true,
      width: "25vw",
      data: {
        message: 'Bạn có muốn xoá tất cả sản phẩm khỏi giỏ hàng?'
      }
    }).afterClosed().subscribe(result => {
      if (result === Constants.RESULT_CLOSE_DIALOG.CONFIRM) {
        this.cartService.deleteAllByCustomerId(this.storageService.getIdFromToken());
        this.cartService.isReload.subscribe((result) => {
          if (result) {
            this.findAllByCustomerId();
            this.cartService.isReload.next(false);
          }
        })
      }
    })
  }

  updateCart(event: any, customerId?: any, productDetailId?: any, quantity?: any, cartQuantity?: any) {
    const data = {
      customer: {
        id: this.storageService.getIdFromToken(),
      },
      productsDetail: {
        id: productDetailId
      },
      quantity: event.target.value
    }

    if (data.quantity == '' || data.quantity === undefined || data.quantity === null || data.quantity == 0) {
      event.target.value = cartQuantity;
      return;
    }

    if (data.quantity > quantity) {
      event.target.value = cartQuantity;
      this.toastService.warning("Số không được lớn hơn số lượng còn lại !")
      return;
    }
    this.cartService.updateCart(data).subscribe(data => {
      if (data) {
        this.findAllByCustomerId();
        if (this.districtId !== undefined) {
          this.getShippingFee(this.districtId);
        }
        if (this.defaultInfoModel) {
          this.getShippingFee(this.address.districtId)
        }
        this.cartService.isReload.next(false);
      }
    })
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
    this.districtId = districtId;
  }

  resetDistrictAndWard() {
    this.formGroup.patchValue({district: -1});
    this.formGroup.patchValue({ward: -1});
    this.districts = [];
    this.wards = [];
    this.shippingTotal = 0;
  }

  resetWard() {
    this.formGroup.patchValue({ward: -1});
    this.wards = [];
  }

  getWardName(wardName: any) {
    this.wardName = wardName;
    this.getShippingFee(this.districtId);
  }

  //Api tinh phí vận chuyển
  getShippingFee(districtId: any) {
    const data = {
      "shop_id": 3424019,
      "from_district": 3440,
      "to_district": districtId
    }
    //Get service để lấy ra phương thức vận chuyển: đường bay, đường bộ,..
    this.addressService.getService(data).subscribe((res: any) => {
      this.serviceId = res.data && res.data.length > 1 ? res.data[1].service_id : res.data[0].service_id;
      const shippingOrder = {
        "service_id": this.serviceId,
        "insurance_value": this.subTotal,
        "from_district_id": 3440,
        "to_district_id": data.to_district,
        // "to_ward_code": "20314",
        "weight": this.weight
      }
      //getShippingOrder tính phí vận chuyển
      this.addressService.getShippingOrder(shippingOrder).subscribe((res: any) => {
        this.shippingTotal = res.data.total;
      })
    })
  }

  defaultInfo(event: any) {
    if (event.target.value == 'on') {
      this.defaultInfoModel = true;
      event.target.value = 'off';
      this.getShippingFee(this.address.districtId)
      this.formGroup.patchValue({
        province: -1,
        district: -1,
        ward: -1,
        shipPhone: '',
        shipName: '',
        other: ''
      })
    } else {
      this.defaultInfoModel = false;
      event.target.value = 'on';
      this.shippingTotal = 0;
    }
    this.defaultInfoModel ? this.formGroup.disable() : this.formGroup.enable();
    this.formGroup.get('note')?.enable();
  }

  checkOut() {
    const address = [];
    const shipPhone = this.formGroup.getRawValue().shipPhone;
    const shipName = this.formGroup.getRawValue().shipName;
    const note = this.formGroup.getRawValue().note;
    const other = this.formGroup.getRawValue().other;

    let order = {
      shipAddress: '',
      shipPhone: '',
      shipName: '',
      note: note?.trim(),
      customer: {
        id: this.storageService.getIdFromToken()
      },
      employee: {
        id: 1
      },
      total: this.subTotal,
      freight: this.shippingTotal,
      status: Constants.ORDER_STATUS.WAITING
    }

    if (this.carts.length == 0) {
      this.toastService.warning("Giỏ hàng của bạn đang trống !")
      return;
    }
    console.log(this.carts);

    // for (let i = 0; i < this.carts.length; i++) {
    //   if (this.carts[i].productsDetail.product.status == 0) {
    //     this.toastService.warning('Đơn hàng của bạn có sản phẩm đã ngừng kinh doanh');
    //     this.findAllByCustomerId();
    //     return;
    //   }

    // }

    if (!this.defaultInfoModel) {
      if (this.formGroup.getRawValue().district === -1 ||
        this.formGroup.getRawValue().province === -1 ||
        this.formGroup.getRawValue().ward === -1 ||
        shipPhone?.trim() === '' || shipName?.trim() === '' || other?.trim() === '') {
        this.toastService.warning("Vui lòng nhập đầy đủ thông tin !")
        return;
      }
      if (!shipPhone?.trim().match("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
        this.toastService.warning("Số điện thoại không đúng định dạng !")
        return;
      }
      if (this.formGroup.getRawValue().other !== '') {
        address.push(other?.trim(), this.wardName, this.districtName, this.provinceName);
      } else {
        address.push(this.wardName, this.districtName, this.provinceName);
      }
      order.shipAddress = address.join(", ")
      order.shipPhone = shipPhone.trim()
      order.shipName = shipName!.trim()
    } else {
      if (!this.address) {
        this.toastService.warning("Vui lòng chọn địa chỉ mặc định trước khi thanh toán !");
        return;
      }
      if (this.address.other != '') {
        address.push(this.address.other, this.address.wardName, this.address.districtName, this.address.provinceName);
      } else {
        address.push(this.address.wardName, this.address.districtName, this.address.provinceName);
      }
      order.shipAddress = address.join(", ")
      order.shipPhone = this.address.phone
      order.shipName = this.address.fullname
    }
    const checkOutData = {
      order: order,
      listCarts: this.carts
    }

    this.matDialog.open(ConfirmDialogComponent, {
      disableClose: true,
      hasBackdrop: true,
      width: "25vw",
      data: {
        message: 'Bạn có muốn đặt hàng?'
      }
    }).afterClosed().subscribe((result) => {
      if (result === Constants.RESULT_CLOSE_DIALOG.CONFIRM) {
        this.orderService.createOrder(checkOutData).subscribe({
            next: (res) => {
              this.toastService.success("Đặt hàng thành công !")
              this.cartService.deleteAllByCustomerId(this.storageService.getIdFromToken());
              this.cartService.isReload.subscribe(rs => {
                if (rs) {
                  this.cartService.findAllByCustomerId(this.storageService.getIdFromToken());
                  this.carts = [];
                  this.subTotal = 0;
                  this.cartService.isReload.next(false);
                }
              })
              void this.route.navigate(["/profile/user-order"]);
            },
            error: (err) => {
              if (err.error.code == 'LIMIT_QUANTITY' || err.error.code == 'NOT_FOUND') {
                this.toastService.warning(err.error.message);
                this.findAllByCustomerId();
                return;
              }
              this.toastService.error("Đặt hàng không thành công !");
            }
          }
        )
      }
    })

  }

  openEditAddressDialog() {
    if (this.address != null) {
      let idAddress = this.address.id;
      this.matDialog.open(EditAddressComponent, {
        width: '50vw',
        height: '21w',
        disableClose: true,
        hasBackdrop: true,
        data: {
          id: idAddress
        }
      }).afterClosed().subscribe(data => {
        if (data !== 'close' && data !== undefined) {
          this.addressService.findAddressById(data).subscribe(data => {
            this.address = data;
            this.getShippingFee(this.address.districtId)
          })
        }
      })
    } else {
      this.matDialog.open(EditAddressComponent, {
        width: '50vw',
        height: '21w',
        disableClose: true,
        hasBackdrop: true,
      }).afterClosed().subscribe(data => {
        if (data !== 'close' && data !== undefined) {
          this.addressService.findAddressById(data).subscribe(data => {
            this.address = data;
            this.getShippingFee(this.address.districtId)
          })
        }
      })
    }
  }

  findAddressByStatus(customerId: number) {
    this.addressService.findAddressByStatus(customerId).subscribe((res: any) => {
      this.address = res;
    })
  }


}

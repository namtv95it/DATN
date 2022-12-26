import {Component, OnInit, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {OrderDetailService} from "../../../../../shared/service/order-detail/order-detail.service";
import {FormBuilder} from "@angular/forms";
import {MatStepper} from "@angular/material/stepper";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {ToastrService} from "ngx-toastr";
import {UploadCloudinaryService} from "../../../../../shared/service/cloudinary/upload-cloudinary.service";
import {ExchangeService} from "../../../../../shared/service/exchange/exchange.service";
import {ConfirmDialogComponent} from "../../../../../shared/confirm-dialog/confirm-dialog.component";
import {Constants} from "../../../../../shared/constants/constants.module";
import {UserOrderComponent} from "../user-order.component";

@Component({
  selector: 'app-exchange-order-dialog',
  templateUrl: './exchange-order-dialog.component.html',
  styleUrls: ['./exchange-order-dialog.component.css'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS,
    useValue: {displayDefaultIndicatorType: false, showError: true}
  }]
})
export class ExchangeOrderDialogComponent implements OnInit {

  displayedColumns: string[] = ['select', 'stt', 'productImage', 'productName', 'color', 'size', 'quantityOld', 'quantity'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  displayedColumnsExchange: string[] = ['stt', 'productImage', 'productName', 'color', 'size', 'quantity', 'status'];
  dataSourceExchange: MatTableDataSource<any> = new MatTableDataSource<any>();
  selection = new SelectionModel<any>(true, []);

  readonly TYPE_EXCHANGE = {
    CHO_XAC_NHAN: 0,
    NHAN_HANG: 1,
    DONG_Y_TRA: 2,
    HUY_TRA: 3
  }

  orderDetail: any[] = [];
  fileUploadImage: any[] = [];
  fileUploadVideo: any[] = [];
  urlImage!: any;
  urlVideo!: any;
  quantity: number = 0;
  formGroup = this.fb.group({
    id: [''],
    quantity: ['']
  });

  reason: string = '';
  dataExchange: any[] = [];
  isLoading: boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dataDialog: any,
    private readonly orderDetailService: OrderDetailService,
    private readonly fb: FormBuilder,
    private readonly toastService: ToastrService,
    private readonly uploadService: UploadCloudinaryService,
    private readonly exchangeService: ExchangeService,
    private readonly matDialog: MatDialog,
    private readonly matDialogRef: MatDialogRef<UserOrderComponent>
  ) {
  }

  ngOnInit() {
    if (this.dataDialog.checkExchange === 0) {
      this.orderDetailService.getOrderDetailByOrderId(this.dataDialog.order.id).subscribe(res => {
        this.orderDetail = res as any[];
        this.dataSource = new MatTableDataSource<any>(this.orderDetail);
        this.dataSource.data = this.orderDetail;
      })
    } else {
      this.orderDetailService.getOrderDetailsInExchange(this.dataDialog.order.id).subscribe((res: any) => {
        this.dataSourceExchange = new MatTableDataSource<any>(res);
        this.dataSourceExchange.data = res;
      })
    }
  }


  onChooseProduct(stepper: MatStepper) {
    const dataOld = this.dataSource.data.map(s => {
      return {
        id: s.productsDetail.id,
        quantity: s.quantity
      }
    })
    console.log(dataOld);
    console.log(this.dataExchange)
    if (this.dataExchange.length > 0) {
      for (let i = 0; i < this.dataExchange.length; i++) {
        if (this.dataExchange[i].quantity === 0 || this.dataExchange[i].quantity < 0 || this.dataExchange[i].quantity === '') {
          this.toastService.error(`Số lượng hoàn trả không hợp lệ. Vui lòng nhập lại !`);
          return;
        }
      }
    }
    this.goForward(stepper);
  }

  onChooseReason(stepper: MatStepper) {
    if (this.fileUploadImage.length <= 0) {
      this.toastService.error("Vui lòng chọn hình ảnh !")
      return;
    }
    if (this.fileUploadVideo.length <= 0) {
      this.toastService.error("Vui lòng chọn video !")
      return;
    }
    if (this.reason === '') {
      this.toastService.error("Vui lòng nhập lý do hoàn trả !")
      return;
    }
    this.goForward(stepper);
  }

  onConfirm() {
    this.matDialog.open(ConfirmDialogComponent, {
      disableClose: true,
      hasBackdrop: true,
      data: {
        message: 'Bạn có muốn hoàn trả đơn hàng này ?'
      }
    }).afterClosed().subscribe(result => {
      if (result === Constants.RESULT_CLOSE_DIALOG.CONFIRM) {
        this.save()
          .then(() => {
            this.toastService.success("Hoàn trả thành công !");
            this.matDialogRef.close(Constants.RESULT_CLOSE_DIALOG.SUCCESS);
          })
          .catch(() => {
            this.toastService.error("Hoàn trả thất bại. Vui lòng thử lại !");
          })
          .finally(() => {
            this.isLoading = false;
          })
      }
    })
  }

  async save() {
    this.isLoading = true;
    const formData = new FormData();
    const formDataVideo = new FormData();
    for (let i = 0; i < this.fileUploadImage.length; i++) {
      formData.append('files', this.fileUploadImage[i]);
    }
    for (let i = 0; i < this.fileUploadVideo.length; i++) {
      formDataVideo.append('files', this.fileUploadVideo[i]);
    }
    if (this.fileUploadImage.length > 0) {
      this.urlImage = await this.uploadService.upload(formData).toPromise();
    }
    if (this.fileUploadVideo.length > 0) {
      this.urlVideo = await this.uploadService.upload(formDataVideo).toPromise();
    }

    const exchangePayload = {
      video: this.urlVideo[0],
      reason: this.reason.trim(),
      createDate: new Date(),
      status: 0
    }

    const exchange = await this.exchangeService.createExchange(exchangePayload).toPromise();
    for (let i = 0; i < this.urlImage.length; i++) {
      await this.exchangeService.createExchangeImage({exchange, name: this.urlImage[i]}).toPromise();
    }

    const dataOld = this.orderDetail.map(o => {
      return {
        id: o.id,
        quantity: o.quantity,
      }
    })

    for (let i = 0; i < this.dataExchange.length; i++) {
      for (let j = 0; j < dataOld.length; j++) {
        if (this.dataExchange[i].id === dataOld[j].id) {
          if (this.dataExchange[i].quantity !== dataOld[j].quantity) {
            //Tao moi order detail
            this.orderDetailService.saveOrderDetailExchange({
              id: this.dataExchange[i].id,
              unitprice: this.dataExchange[i].quantity * this.dataExchange[i].price,
              exchanges: exchange,
              quantity: this.dataExchange[i].quantity,
            }).subscribe(res => console.log(res))
          } else {
            //Cap nhat status
            this.orderDetailService.updateOrderDetailExchange({
              id: this.dataExchange[i].id,
              exchanges: exchange
            }).subscribe(res => console.log(res));
          }
        }
      }
    }

  }

  setQuantity($event: any, i: any, row: any) {
    this.quantity = $event.target.value;
    this.selectRow(row);
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource?.data.length;
    return numSelected === numRows;
  }

  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    for (let i = 0; i < this.dataSource.data.length; i++) {
      this.dataSource.data[i].quantityNew = 0;
    }
    this.selection.select(...this.dataSource.data);
    for (let i = 0; i < this.selection.selected.length; i++) {
      this.selectRow(this.selection.selected[i]);
    }
  }

  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;
  }


  onUploadImage($event: any) {
    this.fileUploadImage = $event.addedFiles;
  }

  onRemoveImage(f: any) {
    this.fileUploadImage.splice(this.fileUploadImage.indexOf(f), 1);
  }

  onUploadVideo($event: any) {
    this.fileUploadVideo = $event.addedFiles;
  }

  onRemoveVideo(f: any) {
    this.fileUploadVideo.splice(this.fileUploadVideo.indexOf(f), 1);
  }

  onChangeReason($event: any) {
    this.reason = $event.target.value !== '' ? String($event.target.value).trim() : '';
  }


  selectRow(row?: any) {
    if (this.selection.selected.length > 0) {
      for (let i = 0; i < this.selection.selected.length; i++) {
        if (this.selection.selected[i].id == row?.id) {
          this.selection.selected[i].quantityNew = Number(this.quantity);
          this.selection.selected[i].productName = this.selection.selected[i].productsDetail.product.name;
          this.selection.selected[i].size = this.selection.selected[i].productsDetail.size.code;
          this.selection.selected[i].color = this.selection.selected[i].productsDetail.color.name;
          this.selection.selected[i].price = this.selection.selected[i].unitprice;
        }
      }
    }
    this.quantity = 0;
    this.dataExchange = this.selection.selected.map(s => {
      return {
        id: s.id,
        quantity: s.quantityNew,
        productName: s.productName,
        size: s.size,
        color: s.color,
        price: s.price
      }
    })
    console.log(this.dataExchange);
  }


  goBack(stepper: MatStepper) {
    stepper.previous();
  }

  goForward(stepper: MatStepper) {
    stepper.next();
  }


  isDisabled(row: any) {
    for (const e of this.selection.selected) {
      if (e.id === row.id) {
        return true;
      }
    }
    return false;
  }

  isValidQuantity() {
    for (let i = 0; i < this.dataExchange.length; i++) {
      if (this.dataExchange[i].quantity === 0 || this.dataExchange[i].quantity < 0 || this.dataExchange[i].quantity === '') {
        return false;
      }
    }
    return true;
  }
}

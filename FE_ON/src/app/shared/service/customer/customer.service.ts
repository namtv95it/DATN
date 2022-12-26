import {Injectable} from "@angular/core";
import {CustomerApiService} from "./customer-api.service";
import {ToastrService} from "ngx-toastr";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: "root",
})
export class CustomerService {
  isCloseDialog: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  response: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(
    private readonly apiService: CustomerApiService,
    private readonly toastService: ToastrService
  ) {
  }

  getCustomer(id: number) {
    return this.apiService.getCustomer(id);
  }

  updateCustomer(data: any, id: number) {
    return this.apiService.updateCustomer(data, id).subscribe({
      next: (res) => {
        if (res.id) {
          this.toastService.success("Cập nhật khách hàng thành công !");
          this.isCloseDialog.next(true);
        }
      },
      error: (err) => {
        console.log(err);
        this.toastService.error("Cập nhật khách hàng thất bại !");
        this.isCloseDialog.next(false);
      },
    });
  }


}

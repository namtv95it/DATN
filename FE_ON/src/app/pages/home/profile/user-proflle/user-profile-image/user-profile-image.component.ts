import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {UserProflleComponent} from "../user-proflle.component";
import {CustomerService} from "../../../../../shared/service/customer/customer.service";
import {StorageService} from "../../../../../shared/service/storage.service";
import {UploadCloudinaryService} from "../../../../../shared/service/cloudinary/upload-cloudinary.service";
import {Constants} from "../../../../../shared/constants/constants.module";

@Component({
  selector: 'app-user-profile-image',
  templateUrl: './user-profile-image.component.html',
  styleUrls: ['./user-profile-image.component.css']
})
export class UserProfileImageComponent implements OnInit {

  title = 'Cập nhập hình ảnh'
  avatarFile: any[] = [];
  avatarUrl: any;
  employee: any;

  constructor(private dialogRef: MatDialogRef<UserProflleComponent>,
              private customerService: CustomerService,
              private storageService: StorageService,
              private uploadService: UploadCloudinaryService) {
  }

  ngOnInit(): void {
  }

  onDismiss() {
    this.dialogRef.close()
  }

  onChangeAvatar(event: any) {
    this.avatarFile = event.addedFiles;
  }

  saveImage() {
    this.uploadImage()
  }

  async uploadImage() {
    const formData = new FormData();
    formData.append('files', this.avatarFile[0]);
    this.avatarUrl = await this.uploadService.upload(formData).toPromise();
    this.customerService.getCustomer(this.storageService.getIdFromToken()).subscribe(res => {
      res.photo = this.avatarUrl[0];
      this.customerService.updateCustomer(res, res.id);
      this.customerService.isCloseDialog.subscribe(
        value => {
          if (value) {
            const data = {
              type: Constants.RESULT_CLOSE_DIALOG.SUCCESS,
              url: this.avatarUrl[0]
            }
            this.dialogRef.close(data);
            this.customerService.isCloseDialog.next(false);
          }
        }
      )
    })

  }

  onRemove(f: any) {
    this.avatarFile.splice(this.avatarFile.indexOf(f), 1);
  }

}

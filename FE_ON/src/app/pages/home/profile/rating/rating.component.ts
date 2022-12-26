import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {UserOrderComponent} from "../user-order/user-order.component";
import {Constants} from "../../../../shared/constants/constants.module";
import {FormBuilder, Validators} from "@angular/forms";
import {RatingService} from "../../../../shared/service/rating-service/rating.service";
import {ToastrService} from "ngx-toastr";
import {RatingImageService} from "../../../../shared/service/rating-image-service/rating-image.service";
import {UploadCloudinaryService} from "../../../../shared/service/cloudinary/upload-cloudinary.service";

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {
  ratingDisplay: number = 4;
  orderId!: number ;
  productId!: number ;
  employeeId!:number ;
  files: File[] = [];
  urlFile: any ;
  checkSprint = false ;

  onRatingSet(rating: number): void {
    this.ratingDisplay = rating ;
  }

  formRating = this.fb.group( {
    id: '' ,
    rating: 0,
    content: ['' , Validators.required ],
    status: 0,
    orders: this.fb.group( {
      id: ''
    }),
    product: this.fb.group( {
      id: ''
    }),
    employee: this.fb.group( {
      id: ''
    }),
    createDate: new Date()
  })

  formImageRating = this.fb.group( {
      id: '' ,
      name: '' ,
      rating: this.fb.group( {
        id: ''
      })
  })

  constructor( private fb: FormBuilder ,
               private toast: ToastrService ,
               private ratingService: RatingService ,
               private ratingImgService: RatingImageService ,
               private cloudinaryService: UploadCloudinaryService ,
               public dialogRef: MatDialogRef<UserOrderComponent> ,
               @Inject(MAT_DIALOG_DATA) public data: any ) {

  }

  ngOnInit(): void {
     this.orderId = this.data.order_id
     this.productId = this.data.product_id
     this.employeeId = this.data.employee_id
  }

  onSelect( event:any ) {
    console.log(event);
    this.files.push(...event.addedFiles);
  }

  onRemove(event:any) {
    console.log(event);
    this.files.splice(this.files.indexOf(event), 1);
  }

  onCancel(){
    this.dialogRef.close( Constants.RESULT_CLOSE_DIALOG.CLOSE )
  }

  onSave(){
    this.formRating.markAllAsTouched()
    if( this.formRating.invalid ){
       return ;
    }
    // @ts-ignore
    this.formRating.value.rating = this.ratingDisplay ;
    // @ts-ignore
    this.formRating.value.product.id = this.productId ;
    // @ts-ignore
    this.formRating.value.orders.id = this.orderId ;
    // @ts-ignore
    this.formRating.value.employee.id = this.employeeId ;

    this.ratingService.createRating(this.formRating.value).subscribe( {
      next: (data: any) => {
           if( this.files.length > 0 ) {
                this.checkSprint = true ;
                this.uploadImage( data.id ) ;
           }
           if( this.checkSprint == false ){
             this.toast.success("Đánh giá thành công")
             this.dialogRef.close( Constants.RESULT_CLOSE_DIALOG.SUCCESS );
           }
      },
      error: () => {
          this.toast.error("Đánh giá thất bại")
      }
    })
  }

  async uploadImage( id: number ){
    if(this.files.length > 5){
      this.toast.warning("Chỉ chọn được tối đa 5 ảnh !")
      return;
    }
    const formData = new FormData();
    try {
      for( let i=0 ; i < this.files.length ; i++  ){
          formData.append('files', this.files[i] );
      }
      this.urlFile = await this.cloudinaryService.upload(formData).toPromise();
      console.log(this.urlFile )
      // @ts-ignore
      this.formImageRating.value.rating.id = id ;
      for( let i=0 ; i< this.urlFile.length ; i++   ){
          this.formImageRating.patchValue({name: this.urlFile[i]})
          this.ratingImgService.creatRatingImg(this.formImageRating.value).subscribe() ;
      }
      this.checkSprint = false ;
      this.toast.success("Đánh giá thành công")
      this.dialogRef.close( Constants.RESULT_CLOSE_DIALOG.SUCCESS );
    } catch (err) {
      console.log(err);
    }
  }
}

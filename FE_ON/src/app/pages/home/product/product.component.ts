import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../../../shared/service/product-service/product.service";
import {ProductViewComponent} from "../home/product-view/product-view.component";
import {MatDialog} from "@angular/material/dialog";
import {SizeService} from "../../../shared/service/size-service/size.service";
import {ColorService} from "../../../shared/service/color-service/color.service";
import {FormBuilder, Validators} from "@angular/forms";
import {checkPrice} from "../../../shared/validators/checkPrice";
import {CategoryService} from "../../../shared/service/category-service/category.service";
import {RatingService} from "../../../shared/service/rating-service/rating.service";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  listPro: any;
  listSize: any;
  litsCate: any;
  listColor: any ;
  listRating: any[] = [] ;

  listProBySize = [];
  listProByCate = [];
  listProByColor = [];

  defaultValueSize = [];
  defaultValueCategory = [];
  defaultValueColor = [];

  totalPage: any ;
  listPage = [] ;
  pagaNo = 0 ;
  checkPage = 0 ;
  pageSize = 8 ;
  sortPrice = 0 ;
  check = true ;
  checkLoadData = true ;

  formPrice = this.fb.group( {
      max: ['' ] ,
      min: ['' ]
  }, {
      validators: checkPrice
  })

  constructor(private fb:FormBuilder ,
              private proService: ProductService,
              private sizeService: SizeService,
              private dialog: MatDialog,
              private cateService: CategoryService ,
              private colorService: ColorService ,
              private ratingService: RatingService ) {

    setTimeout( () => {
      this.getAllColor() ;
      this.getAllSize();
      this.getAllCategory();
          setTimeout( () => {
             this.findAll();
             this.checkLoadData = false ;
             } , 500)
    }, 500)

    this.getAvgRating() ;
    // this.getAllProSize();
    // this.getAllPro();
  }

  getAvgRating(){
     this.ratingService.getArgRating().subscribe( data => {
         this.listRating = data as any[]
     })
  }

  getAllCategory() {
    this.cateService.getAllCategory().subscribe(data => {
      this.litsCate = data
      this.changeValue(data, this.defaultValueCategory);
    })
  }

  getAllSize() {
    this.sizeService.getAllSize().subscribe(data => {
      this.listSize = data;
      this.changeValue(data, this.defaultValueSize);
    })
  }

  getAllColor(){
     this.colorService.getAllByColor().subscribe( data => {
         this.listColor = data ;
         this.changeValue( data , this.defaultValueColor )
     })
  }

  changeValue(a: any, b: any) {
    for (var aa of a) {
      b.push(aa.id)
    }
  }

  getAllProByAllProperty(size: any, cate: any , color: any , max: number , min: number , pageNo: number , pageSize: number , sortPrice: number) {
    this.proService.getAllProByAllProperty(size, cate , color , max , min , pageNo , pageSize , sortPrice ).subscribe((data:any) => {
      this.listPro = data.content ;
      this.totalPage = data.totalPages ;
      this.changPage( this.totalPage , this.listPage  )
    })
  }

  ngOnInit(): void {

  }

  OpenProductView(product: any) {
    const dialogRef = this.dialog.open(ProductViewComponent, {
      width: '60vw',
      disableClose: false,
      hasBackdrop: true,
      data: {
        product: product,
        type: 'pro'
      }
    })
    dialogRef.afterClosed().subscribe(value => {

    })
  }

  //Tìm theo Size
  getAllBySize(size: number) {
    // @ts-ignore
    if (this.listProBySize.includes(size)) {
      // @ts-ignore
      this.listProBySize.splice(this.listProBySize.indexOf(size), 1)
    } else {
      // @ts-ignore
      this.listProBySize.push(size)
    }
    this.clearPage() ;
    this.findAll() ;
  }

  // tìm theo category
  getAllByCategory(cate: number) {
    // @ts-ignore
    if (this.listProByCate.includes(cate)) {
      // @ts-ignore
      this.listProByCate.splice(this.listProByCate.indexOf(cate), 1)
    } else {
      // @ts-ignore
      this.listProByCate.push(cate)
    }

    this.clearPage() ;
    this.findAll() ;
  }
  //Tìm theo color
  getAllByColor( color: number ) {
    // @ts-ignore
    if (this.listProByColor.includes(color)) {
      // @ts-ignore
      this.listProByColor.splice(this.listProByColor.indexOf(color), 1)
    } else {
      // @ts-ignore
      this.listProByColor.push(color)
    }
    this.clearPage() ;
    this.findAll() ;

  }

  getAllByPrice(){
     this.formPrice.markAllAsTouched() ;
     if( this.formPrice.invalid ){
       return  ;
     }

     this.clearPage();
     this.check = false ;
     this.findAll()
  }

  // Tìm kiếm theo các trường
  async findAll() {
    var size: any;
    var cate: any;
    var color: any ;

    if( this.check ){
       this.formPrice.reset()
    }

    if (this.listProBySize.length == 0) {
      size = this.defaultValueSize
    } else {
      size = this.listProBySize;
    }

    if (this.listProByCate.length == 0) {
      cate = this.defaultValueCategory;
    } else {
      cate = this.listProByCate
    }

    if( this.listProByColor.length == 0 ){
      color = this.defaultValueColor ;
    }else{
      color = this.listProByColor
    }

    var max = Number( this.formPrice.value.max  ) ;
    var min = Number( this.formPrice.value.min ) ;
    this.listPage= []
    this.getAllProByAllProperty(size, cate , color , max , min , this.pagaNo , this.pageSize , this.sortPrice  ) ;
  }


//  phân trang
  changPage( page: number , a: any ){
     for( var i=0 ; i<page ; i++ ){
        a.push(i)
     }
  }

  nextPage( a: number){
     this.pagaNo = a ;
     this.checkPage = a ;
     this.findAll() ;
  }

  showProInPage(){
     this.clearPage()
     this.findAll()
  }

  showProSort(){
     this.clearPage()
     this.findAll() ;
  }

  clearPage(){
    this.pagaNo = 0 ;
    this.checkPage = 0 ;
  }

  takeRatingPro( id: number){
    for( let x of this.listRating ){
      if( x.id == id ){
        return x.numberStar ;
      }
    }
    return 5 ;
  }

  showPage( check: number , page: number ){
     if( page == check || check - 1 == page || check - 2 == page || check + 1 == page || check + 2 == page ){
         return true ;
     }
     return false ;
  }
}

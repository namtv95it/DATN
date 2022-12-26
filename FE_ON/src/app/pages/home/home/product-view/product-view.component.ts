import {Component, Inject, OnInit} from '@angular/core';
import {ProductService} from "../../../../shared/service/product-service/product.service";
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import {HomeComponent} from "../home.component";
import {Constants} from "../../../../shared/constants/constants.module";
import {SizeService} from "../../../../shared/service/size-service/size.service";
import {ColorService} from "../../../../shared/service/color-service/color.service";
import {ProductDetailService} from "../../../../shared/service/product-detail-service/product-detail.service";
import {BehaviorSubject, Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {CartService} from "../../../../shared/service/cart-service/cart-service";
import {ToastrService} from "ngx-toastr";
import {StorageService} from "../../../../shared/service/storage.service";
import {RatingService} from "../../../../shared/service/rating-service/rating.service";
import {RatingImageService} from "../../../../shared/service/rating-image-service/rating-image.service";
import {NgForm} from "@angular/forms";
import { GuideSizeComponent } from './guide-size/guide-size.component';

@Component({
  selector: 'app-product-view',
  templateUrl: './product-view.component.html',
  styleUrls: ['./product-view.component.css']
})
export class ProductViewComponent implements OnInit {
  productId: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  totalQuantity: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  test: any = ''
  product: any;
  productImage: any;
  thumnail: string = '';
  carts: any[] = [];

  sizeModel: any;
  colorModel: any;
  countProductDetail!: number;
  total: any;
  productDetail: any[] = [];
  productQuantity: any;
  message!: string;

  listPro: any;
  checkLength = 0;

  constructor(private route: ActivatedRoute,
              private ServicePro: ProductService,
              private readonly productDetailService: ProductDetailService,
              private readonly colorService: ColorService,
              private readonly sizeService: SizeService,
              private readonly cartService: CartService,
              private readonly toastService: ToastrService,
              private readonly storageService: StorageService,
              private readonly ratingService: RatingService,
              private readonly ratingImageService: RatingImageService,
              private readonly matDialog: MatDialog,
              private readonly router: Router,
              @Inject(MAT_DIALOG_DATA) public matData: any) {
  }

  ngOnInit(): void {

    this.getProductById(this.matData.product.id);
    this.totalQuantity.subscribe(res => {
      if (res > 0) {
        this.total = res;
      }
    })
    this.productQuantity = 1;
    if (this.storageService.isLoggedIn()) {
      this.findAllByCustomerId();
    }
  }

  slideConfissg = {
    slidesToShow: 3, slidesToScroll: 1, vertical: true, draggable: false, infinite: false,
    nextArrow: ' <button type="button" style="z-index: 3" class="text-white btn btn-dark opacity-50 position-absolute bottom-0 start-50 translate-middle-x"><i class="fas fa-arrow-down"></i></button>',
    prevArrow: '<button type="button" style="z-index: 3" class="text-white btn btn-dark opacity-50 position-absolute top-0 start-50 translate-middle-x"><i class="fas fa-arrow-up  "></i></button>'
  };

  slideConfig = {
    slidesToShow: 4, slidesToScroll: 1,
    nextArrow: ' <button type="button" style="z-index: 3" class="text-white btn btn-dark opacity-50 position-absolute top-50  end-0 translate-middle-y"><i class="fas fa-chevron-right"></i></button>',
    prevArrow: '<button type="button" style="z-index: 3" class="text-white btn btn-dark opacity-50 position-absolute top-50 start-0 translate-middle-y"><i class="fas fa-chevron-left"></i></button>'
  };

  slickInit(e: any) {
    // console.log('slick initialized');
  }

  breakpoint(e: any) {
    // console.log('breakpoint');
  }

  afterChange(e: any) {
    // console.log('afterChange');
  }

  beforeChange(e: any) {
    // console.log('beforeChange');
  }

  getProductSimilar(idCate: number) {
    this.ServicePro.getProductSimilar(idCate).subscribe(data => {
      this.listPro = data
      this.checkLength = this.listPro.length;
    })
  }

  getProductById(productIdFromRoute: number) {
    this.ServicePro.getProductById(productIdFromRoute).subscribe(value => {
      if (value) {
        this.thumnail = value.thumnail;
        this.product = value;
        this.productId.next(value.id);
        this.getProductSimilar(this.product.category.id);
        this.getProductImageById(value.id);
        this.getProductDetailByProductId(value.id);
      }
    })
  }

  openGuideSize(){
    this.matDialog.open(GuideSizeComponent,{
      width: '1000px',
      disableClose: true
    })
  }

  getProductImageById(productId: number) {
    this.ServicePro.getProductImageById(productId).subscribe(value => {
      this.productImage = value;
    })
  }

  listColor: any = [];
  listSize: any = [];
  size: any = [];
  color: any = [];

  getProductDetailByProductId(productId: number) {
    let mapSize = new Map();
    let mapColor = new Map();
    this.productDetailService.getProductDetailByProductId(productId).subscribe({
      next: (res: any) => {
        this.productDetail = res;
        this.productDetail.forEach(p => {
          mapSize.set(p.size.id, p.size.code);
          mapColor.set(p.color.id, {name: p.color.name, code: p.color.code});
        })
        mapSize.forEach((v, k) => {
          this.listSize.push({id: k, code: v})
        })
        mapColor.forEach((v, k) => {
          this.listColor.push({id: k, code: v})
        })
        this.listSize = this.listSize.sort((a: { id: number; }, b: { id: number; }) => a.id - b.id);
        this.totalQuantity.next(res.map((p: any) => p.quantity).reduce((value: any, total: any) => value + total, 0))
      },
      error: (err) => {
      }
    })
  }

  changThumnail(img: string) {
    this.thumnail = img;
  }

  onClickColor(id: any) {
    if (this.listColor.id === undefined || this.listColor.id != id) {
      this.size = this.productDetail.filter(p => p.color.id === id && p.quantity > 0).map(p => p.size.id);
      this.listColor.id = id;
    } else {
      this.listColor.id = undefined;
      this.size = [];
    }
  }

  onClickSize(id: any) {
    if (this.listSize.id === undefined || this.listSize.id != id) {
      this.color = this.productDetail.filter(p => p.size.id === id && p.quantity > 0).map(p => p.color.id);
      this.listSize.id = id;
    } else {
      this.listSize.id = undefined;
      this.color = [];
    }
  }

  onClickProductDetail() {
    this.productQuantity = 1;
    let pid = 0;
    this.productId.subscribe((id: number) => {
      pid = id;
    })

    if (this.sizeModel !== undefined && this.colorModel !== undefined) {
      this.productDetailService.getProductDetailByProductId(pid).subscribe({
        next: (res: any) => {
          res = res.filter((p: any) => p.color.id === this.colorModel && p.size.id === this.sizeModel && p.product.id === pid)
            .map((p: any) => p.quantity)
          this.countProductDetail = res;
        },
        error: (err) => {
          console.log(err);
        }
      })
    }
  }

  onAddToCart(f: NgForm) {
    if (!this.isLogin()) {
      this.toastService.warning("Vui lòng đăng nhập để tiếp tục !");
      return;
    }

    let pid = 0;
    this.productId.subscribe((id: number) => {
      if (id) {
        pid = id;
      }
    })

    const data = {
      productId: pid,
      sizeId: this.listSize.id,
      colorId: this.listColor.id
    }

    if (this.listColor.id === undefined || this.listSize.id === undefined) {
      this.message = 'Vui lòng chọn màu sắc và size';
      return;
    } else {
      this.message = ''
    }

    this.productDetailService.findProductDetailBySizeAndColor(data.productId, data.sizeId, data.colorId).subscribe((res: any) => {
      if (this.productQuantity > res.quantity || this.productQuantity <= 0 || isNaN(this.productQuantity)) {
        this.productQuantity = 1;
        this.message = 'Số lượng không hợp lệ. Vui lòng nhập lại !';
        return;
      }

      if (this.carts.length > 0) {
        for (const c of this.carts) {
          if (c.productsDetail.id == res.id && (parseInt(c.quantity) + parseInt(this.productQuantity)) > res.quantity) {
            this.toastService.warning("Sản phẩm còn lại không đủ !");
            return;
          }
        }
      }

      const cart = {
        quantity: f.value.quantity,
        customer: {
          id: this.storageService.getIdFromToken()
        },
        productsDetail: {
          id: res.id
        }
      }


      this.cartService.addToCart(cart).subscribe(data => {
        if (data) {
          this.findAllByCustomerId();
          this.toastService.success("Thêm sản phẩm vào giỏ hàng thành công !")
          this.cartService.isReload.next(false)
        } else {
          this.toastService.error("Thêm sản phẩm vào giỏ hàng thất bại !")
        }
      })

    })
  }

  findAllByCustomerId() {
    this.cartService.findAllByCustomerId(this.storageService.getIdFromToken()).subscribe(res => {
      this.carts = res as any[];
    })
  }

  onChangeInput(event: any) {
    this.productQuantity = event.target.value;
  }

  isLogin() {
    if (!this.storageService.isLoggedIn()) {
      void this.router.navigate(['/sign-in'], {queryParams: {redirectUrl: this.router.url}});
      return false;
    }
    return true;
  }

}

import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../../shared/service/product-service/product.service";
import {MatDialog} from "@angular/material/dialog";
import {ProductViewComponent} from "./product-view/product-view.component";
import {RatingService} from "../../../shared/service/rating-service/rating.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  listPro: any ;
  listNewPro: any ;
  listAvgRating: any[] = [] ;

  constructor( private proService: ProductService ,
               private dialog: MatDialog ,
               private ratingService: RatingService ) {
      this.getTop10Pro() ;
      this.getNewProduct() ;
      this.getAvgRating() ;
  }

  getAvgRating(){
     this.ratingService.getArgRating().subscribe( data => {
         this.listAvgRating = data as any[] ;
     })
  }

  getTop10Pro(){
    this.proService.getTop10Pro().subscribe( data => {
      this.listPro = data
    })
  }

  getNewProduct() {
      this.proService.getNewPro().subscribe( data => {
          this.listNewPro = data
      })
  }

  ngOnInit(): void {
  }

  slideConfig = { slidesToShow: 4, slidesToScroll:1 , swipeToSlide: true  ,
    responsive: [
      {
        breakpoint: 700,
        settings: {
          arrows: false,
          centerMode: true,
          centerPadding: '40px',
          slidesToShow: 2
        }
      },
      {
        breakpoint: 1000,
        settings: {
          arrows: false,
          centerMode: true,
          centerPadding: '40px',
          slidesToShow: 2
        }
      },
      {
        breakpoint: 480,
        settings: {
          arrows: false,
          centerMode: true,
          centerPadding: '40px',
          slidesToShow: 1
        }} ],
    nextArrow: ' <button type="button" style="z-index: 3" class="text-white btn btn-dark opacity-50 position-absolute top-50  end-0 translate-middle-y"><i class="fas fa-chevron-right"></i></button>' ,
    prevArrow: '<button type="button" style="z-index: 3" class="text-white btn btn-dark opacity-50 position-absolute top-50 start-0 translate-middle-y"><i class="fas fa-chevron-left"></i></button>'};

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

  OpenProductView( product: any ){
     const dialogRef = this.dialog.open( ProductViewComponent , {
       width: '60vw' ,
       disableClose: false ,
       hasBackdrop: true ,
       data: {
           product: product ,
           type: 'home'
       }
     })

    dialogRef.afterClosed().subscribe( value => {

    })
  }

  takeRatingPro( id: number){
      for( let x of this.listAvgRating ){
        if( x.id == id ){
           return x.numberStar ;
        }
      }
      return 5 ;
  }
}

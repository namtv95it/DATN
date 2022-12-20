import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutComponent} from './layout/layout/layout.component';
import {HeaderComponent} from './layout/header/header.component';
import {SidebarComponent} from './layout/sidebar/sidebar.component';
import {FooterComponent} from './layout/footer/footer.component';
import {NavbarComponent} from './layout/navbar/navbar.component';
import {ConfirmDialogComponent} from "./shared/confirm-dialog/confirm-dialog.component";
import {HttpClientModule} from "@angular/common/http";
import {CdkTableModule} from "@angular/cdk/table";
import {ProductViewComponent} from "./pages/home/home/product-view/product-view.component";
import {SlickCarouselModule} from "ngx-slick-carousel";
import {ToastrModule} from "ngx-toastr";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {httpInterceptorProviders} from "./shared/intercepror/http-request.interceptor";
import {MatDialogModule} from "@angular/material/dialog";
import {UserOrderModule} from "./pages/home/profile/user-order/user-order.module";


@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    HeaderComponent,
    SidebarComponent,
    FooterComponent,
    NavbarComponent,
    ConfirmDialogComponent,
    ProductViewComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        CdkTableModule,
        ToastrModule.forRoot({
            maxOpened: 1,
            preventDuplicates: true,
            autoDismiss: true,
            progressBar: true,
            timeOut: 2000,
            resetTimeoutOnDuplicate: true
        }),
        FormsModule,
        ReactiveFormsModule,
        SlickCarouselModule,
        MatDialogModule,
        UserOrderModule
    ],
  providers: [httpInterceptorProviders],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

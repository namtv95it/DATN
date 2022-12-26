import {Routes} from "@angular/router";
import {SigninComponent} from "../../pages/home/profile/signin/signin.component";
import {SignupComponent} from "../../pages/home/profile/signup/signup.component";
import {AuthGuard} from "../guard/auth.guard";
import {RoleGuard} from "../guard/role.guard";
import {ForgotPasswordComponent} from "../../pages/home/profile/forgot-password/forgot-password.component";

export const router_user: Routes = [
  {
    path: 'cart',
    loadChildren: () => import('../../pages/home/cart/cart.module').then(m => m.CartModule),
    canActivate: [AuthGuard, RoleGuard],
    data: {
      role: 'ROLE_CUSTOMER'
    }
  },
  {
    path: 'home',
    loadChildren: () => import('../../pages/home/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'about',
    loadChildren: () => import('../../pages/home/about/about.module').then(m => m.AboutModule)
  },
  {
    path: 'contact',
    loadChildren: () => import('../../pages/home/contact/contact.module').then(m => m.ContactModule)
  },
  {
    path: 'profile',
    loadChildren: () => import('../../pages/home/profile/profile.module').then(m => m.ProfileModule),
    canActivate: [AuthGuard, RoleGuard],
    data: {
      role: 'ROLE_CUSTOMER'
    }
  },
  {
    path: 'user-profile',
    loadChildren: () => import('../../pages/home/profile/user-proflle/user-profile.module').then(m => m.UserProfileModule),
    canActivate: [AuthGuard, RoleGuard],
    data: {
      role: 'ROLE_CUSTOMER'
    }
  },
  {
    path: 'user-order',
    loadChildren: () => import('../../pages/home/profile/user-order/user-order.module').then(m => m.UserOrderModule),
    canActivate: [AuthGuard, RoleGuard],
    data: {
      role: 'ROLE_CUSTOMER'
    }
  },
  {
    path: 'address-book',
    loadChildren: () => import('../../pages/home/profile/address-book/address-book.module').then(m => m.AddressBookModule),
    canActivate: [AuthGuard, RoleGuard],
    data: {
      role: 'ROLE_CUSTOMER'
    }
  },
  {
    path: 'products',
    loadChildren: () => import('../../pages/home/product/product.module').then(m => m.ProductModule)
  },
  {
    path: 'sign-in',
    component: SigninComponent
  },
  {
    path: 'sign-up',
    component: SignupComponent
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent
  },
  {
    path: 'product-detail',
    loadChildren: () => import('../../pages/home/product-detail/product-detail.module').then(m => m.ProductDetailModule)
  }
]

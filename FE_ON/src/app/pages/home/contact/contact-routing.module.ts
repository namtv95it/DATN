import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ContactComponent} from "./contact.component";


const router: Routes = [
  {
    path: '',
    component: ContactComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class ContactRoutingModule {
}

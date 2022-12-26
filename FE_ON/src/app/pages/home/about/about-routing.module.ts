import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AboutComponent} from "./about.component";


const router: Routes = [
  {
    path: '',
    component: AboutComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule]
})
export class AboutRoutingModule {
}

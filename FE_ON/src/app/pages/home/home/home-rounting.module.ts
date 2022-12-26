import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home.component";
import {StarsComponent} from "../../../shared/stars/stars.component";
import {ListFormatPipe} from "../../../shared/pipe/list-format.pipe";

const router: Routes = [
  {
    path: '',
    component: HomeComponent
  }
]

@NgModule({
    imports: [RouterModule.forChild(router)],
    exports: [RouterModule]
})
export class HomeRountingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {StarsComponent} from "./stars/stars.component";
import {ListFormatPipe} from "./pipe/list-format.pipe";

@NgModule({
  declarations: [ StarsComponent , ListFormatPipe],
  imports: [
    CommonModule
  ],
  exports: [ StarsComponent, ListFormatPipe]
})
export class SharedModule { }

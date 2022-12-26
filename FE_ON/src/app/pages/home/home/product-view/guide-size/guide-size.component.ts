import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-guide-size',
  templateUrl: './guide-size.component.html',
  styleUrls: ['./guide-size.component.css']
})
export class GuideSizeComponent implements OnInit {

  constructor(
    private matDialogRef: MatDialogRef<GuideSizeComponent>
  ) { }

  ngOnInit() {
  }

  close(){
    this.matDialogRef.close();
  }

}

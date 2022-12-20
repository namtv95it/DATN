import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-stars',
  templateUrl: './stars.component.html',
  styleUrls: ['./stars.component.css']
})
export class StarsComponent implements OnInit {

  @Input() rating: number = 0;
  star = [ 1 ,2 ,3 ,4 ,5]
  constructor() {
  }

  ngOnInit(): void {
  }

}

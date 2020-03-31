import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-wind',
  templateUrl: './wind.component.html',
  styleUrls: ['./wind.component.css']
})
export class WindComponent implements OnInit {

  wind: string;
  dir: string;

  constructor() {
    setInterval(() => {
      this.wind = (Math.random() * (99.9 - 49.9) + 49.9).toFixed(2);
    }, 500);
    setInterval(() => {
      this.dir = (Math.random() * (180 - 130) + 130).toFixed(0);
    }, 490);
  }

  ngOnInit() {
  }

}

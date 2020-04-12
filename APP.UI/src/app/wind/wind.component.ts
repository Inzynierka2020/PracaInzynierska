import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WarningSnackComponent } from './warning-snack/warning-snack.component';

@Component({
  selector: 'app-wind',
  templateUrl: './wind.component.html',
  styleUrls: ['./wind.component.css']
})
export class WindComponent implements OnInit {

  wind = 0.0;
  dir = 0.0;

  regular = true;
  notRegularTime = 50;
  maxNotRegularTime = 5;

  durationInSeconds = 10;
  constructor(private _snackBar: MatSnackBar) {
    setInterval(() => {
      this.wind = (Math.random() * (30 - 0));
      this.dir = (Math.random() * (60 - 0));

      this.regular = false;
      this.notRegularTime += 1;
      if (this.maxNotRegularTime <= this.notRegularTime) {
        this._snackBar.openFromComponent(WarningSnackComponent, {
          duration: this.durationInSeconds * 1000,
        });
        this.notRegularTime = 0;
      }
      if (this.wind > 3 && this.wind < 25)
        if (this.dir > -45 && this.dir < 45) {
          this.regular = true;
          this.notRegularTime=0;
        }
    }, 1000);
  }

  ngOnInit() {
  }

}

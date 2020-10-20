import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WarningSnackComponent } from './warning-snack/warning-snack.component';
import { ClockService } from '../services/clock.service';

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

  private _subscription;
  constructor(private _snackBar: MatSnackBar, private _clockSerice: ClockService) {
    this._subscription = this._clockSerice.getFrame()
      .subscribe(frame => {
        if (frame != 0)
          console.log(frame);
        this.parseFrame(frame + '');
      })
  }

  ngOnDestroy() {
    this._subscription.unsubscribe();
  }

  ngOnInit() {
  }

  parseFrame(frame: String) {
    var values = frame.split(';');
    switch (values[0]) {
      case "$REND": {
        this.openSnack();
        break;
      }
      case "$RWSD": {
        this.wind = parseFloat(values[1])/10.0;
        this.dir = parseFloat(values[2]);
        break;
      }
    }
  }

  openSnack() {
    this._snackBar.openFromComponent(WarningSnackComponent, {
      duration: this.durationInSeconds * 1000,
      data: 'BadWeather'
    });
  }
}

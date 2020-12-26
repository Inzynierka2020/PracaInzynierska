import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WarningSnackComponent } from './warning-snack/warning-snack.component';
import { ClockService } from '../services/clock.service';
import { RulesService } from '../services/rules.service';

@Component({
  selector: 'app-wind',
  templateUrl: './wind.component.html',
  styleUrls: ['./wind.component.css']
})
export class WindComponent implements OnInit {

  wind = 0.0;
  dir = 0.0;

  regular = false;
  notRegularTime = 50;
  maxNotRegularTime = 5;

  durationInSeconds = 10;

  private _subscription;
  constructor(private _snackBar: MatSnackBar, private _clockSerice: ClockService, private _rulesService: RulesService) {
    this._subscription = this._clockSerice.getBehaviourFrameEmitter()
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
      case "$RWRB": {
        this.openSnack();
        break;
      }
      case "$RWSD": {
        this.wind = parseFloat(values[3]) / 10.0;
        this.dir = parseFloat(values[4]);

        var rules = this._rulesService.getRules();
        this.regular = !(this.wind > rules.maxWind || this.wind < rules.minWind || this.dir < rules.minDir || this.dir > rules.maxDir)
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

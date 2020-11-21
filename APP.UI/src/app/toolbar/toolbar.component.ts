import { Component, OnInit, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SettingsComponent } from '../settings/settings.component';
import { Event } from '../models/event';
import { ClockService } from '../services/clock.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent {

  @Input()
  event: Event;

  now: number;
  wind: string;

  constructor(public dialog: MatDialog, public _clock: ClockService) {
    setInterval(() => {
      this.now = Date.now();
    }, 1000);
    setInterval(() => {
      this.wind = (Math.random() * (13.8 - 10.8) + 10.8).toFixed(2);
    }, 500);
  }

  openSettings() {
    const dialogRef = this.dialog.open(SettingsComponent, {
      width: '90%',
      maxWidth: '700px',
      height: '90%',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  isConnectedColor() {
    if (this._clock.isConnected())
      return 'green';
    else
      return 'darkred';
  }
}

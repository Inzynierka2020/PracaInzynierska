import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Flight } from '../models/flight';
import { Pilot } from '../models/pilot';
import { ClockService } from '../services/clock.service';
import { FlightsService } from '../services/flights.service';
import { EventService } from '../services/event.service';
import { TranslateService } from '@ngx-translate/core';
import { take } from 'rxjs/operators';

class PlayerDialogData {
  pilot: Pilot
  flight: Flight
  groupsCount: number
  editMode: boolean
}

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {
  @Input()
  returnDirectly = false;

  editMode = false;
  pilot: Pilot;
  flight: Flight;
  bestFlight: Flight;
  groupsCount: number;
  currentGroup: string;
  groups: string[] = ["A", "B", "C", "D", "E"];

  RCZS_timestamp: number;
  started = false;
  finished = false;

  title = "New player"
  value: string;

  constructor(public dialog: MatDialog,
    public dialogRef: MatDialogRef<PlayerComponent>,
    @Inject(MAT_DIALOG_DATA) private _data: PlayerDialogData,
    private _clockService: ClockService,
    private _flightService: FlightsService,
    private _eventService: EventService,
    private _translate: TranslateService
  ) {
    this.pilot = _data.pilot
    this.flight = _data.flight;
    this.currentGroup = this.flight.group;
    this.groupsCount = this._data.groupsCount;
    this.editMode = _data.editMode;
    this.value = this.flight.order.toString();
    this.bestFlight = this._flightService.getBlankFlight(this.groupsCount);
  }

  private _subscription;
  ngOnInit() {
    this._subscription = this._clockService.getFrame()
      .subscribe(frame => {
        this.parseFrame(frame);
      })
    this._flightService.getBestFlightFromRound(this.flight.roundNum, this._eventService.getEventId()).pipe(take(1)).subscribe(result => {
      if (result != null) {
        this.bestFlight = result;
      }
    })
  }

  ngOnDestroy() {
    this._subscription.unsubscribe();
  }

  timer = false;
  winds = [];
  dirs = [];
  parseFrame(frame: string) {
    if (frame == "0") return;
    var values = frame.split(';');
    switch (values[0]) {
      case "$RCZP": {
        this.title = "CZAS PRZYGOTOWAWCZY";
        this.value = values[1] + " s";
        break;
      }
      case "$RTMO": {
        this.title = "PRZEKROCZONO CZAS PRZYGOTOWAWCZY"
        this.value = "---";
        break;
      }
      case "$RCZS": {
        this.title = "CZAS STARTOWY";
        this.value = values[1] + " s"
        if (values[1] == "30") {
          this.RCZS_timestamp = parseInt(values[2]);
        }
        this.flight.sub1 = 30 - parseInt(values[1]);
        break;
      }
      case "$RNTR": {
        this.title = "CAŁKOWITY CZAS"
        this.value = "0";
        var base = values[2];
        var time = this.round(parseFloat(values[3]) / 100.0);
        if (!this.timer) {
          this.timer = true;
          setInterval(() => {
            if (this.timer)
              this.value = (parseFloat(this.value) + 0.1).toFixed(1).toString();
          }, 100)
        }
        this.winds.push(values[4]);
        this.dirs.push(values[5]);
        switch (base) {
          case "0": {
            var timestamp = parseInt(values[6]);
            this.flight.sub1 = this.round((timestamp - this.RCZS_timestamp) / 100.0);
            this.started = true;
            this.flight.dns = false;
            break;
          }
          case "1": {
            this.flight.sub2 = this.round(time - this.flight.seconds);
            break;
          }
          case "2": {
            this.flight.sub3 = this.round(time - this.flight.seconds);
            break;
          }
          case "3": {
            this.flight.sub4 = this.round(time - this.flight.seconds);
            break;
          }
          case "4": {
            this.flight.sub5 = this.round(time - this.flight.seconds);
            break;
          }
          case "5": {
            this.flight.sub6 = this.round(time - this.flight.seconds);
            break;
          }
          case "6": {
            this.flight.sub7 = this.round(time - this.flight.seconds);
            break;
          }
          case "7": {
            this.flight.sub8 = this.round(time - this.flight.seconds);
            break;
          }
          case "8": {
            this.flight.sub9 = this.round(time - this.flight.seconds);
            break;
          }
          case "9": {
            this.flight.sub10 = this.round(time - this.flight.seconds);
            break;
          }
        }
        this.flight.seconds = time;
        this.value = this.flight.seconds.toFixed(1).toString();
        break;
      }
      case "$REND": {
        this.timer = false;
        this.title = "CAŁKOWITY CZAS"
        var time = this.round(parseFloat(values[3]) / 100.0);
        this.flight.sub11 = this.round(time - this.flight.seconds);
        this.flight.seconds = time;
        this.value = time.toFixed(1).toString();
        this.finished = true;

        this.winds.push(values[4]);
        this.dirs.push(values[5]);

        this.flight.windAvg = this.winds.reduce((a, b) => a + b) / this.winds.length;
        this.flight.dirAvg = this.dirs.reduce((a, b) => a + b) / this.winds.length;
        break;
      }
    }
  }

  didNotFinish() {
    var msg = this._translate.instant("ConfirmDNF");
    if (this.flight.dnf) {
      this.flight.dnf = false;
    } else {
      this.flight.dnf = true;
      this.flight.dns = false;
    }
    if (!this.editMode)
      this.resolveConfirmationDialog(msg).subscribe(confirmed => {
        if (confirmed) {
          this.saveFlight();
        } else {
          if (this.flight.dnf) {
            this.flight.dnf = false;
          } else {
            this.flight.dnf = true;
            this.flight.dns = false;
          }
        }
      })
  }

  didNotStart() {
    var msg = this._translate.instant("ConfirmDNS");
    if (this.flight.dns) {
      this.flight.dns = false;
    } else {
      this.flight.dns = true;
      this.flight.dnf = false;
    }
    if (!this.editMode)
      this.resolveConfirmationDialog(msg).subscribe(confirmed => {
        if (confirmed) {
          this.saveFlight();
        } else {
          if (this.flight.dns) {
            this.flight.dns = false;
          } else {
            this.flight.dns = true;
            this.flight.dnf = false;
          }
        }
      })
  }

  saveFlight() {
    this.flight.synchronized = false;
    this.flight.pilotId = this.pilot.pilotId;
    this.flight.eventId = this.pilot.eventId;
    this.dialogRef.close(this.flight);

  }
  callPressAction() {
    var msg = this._translate.instant("SaveRound");
    this.resolveConfirmationDialog(msg).subscribe(confirmed => {
      if (confirmed) {
        this.saveFlight();
      }
    })
  }

  changeGroup(group: string) {
    this.currentGroup = this.flight.group = group;
  }

  private round(num: number) {
    return Math.round(num * 10) / 10;
  }

  /*---- DIALOG METHODS ----*/
  resolveConfirmationDialog(data = null) {
    return this.dialog.open(ConfirmDialogComponent, {
      width: '80%',
      maxWidth: '500px',
      disableClose: true,
      data: data
    }).afterClosed();
  }

  closeThisDialog(result?) {
    this.resolveConfirmationDialog().subscribe(confirmed => {
      if (confirmed) {
        this.dialogRef.close(result)
      }
    })
  }
}

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
import { BestFlightType, RulesService } from '../services/rules.service';
import { Observable } from 'rxjs';

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

  title: string;
  value: string;

  constructor(public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) private _data: PlayerDialogData,
    public dialogRef: MatDialogRef<PlayerComponent>,
    private _flightService: FlightsService,
    private _translate: TranslateService,
    private _clockService: ClockService,
    private _eventService: EventService,
    private _rulesService: RulesService,
  ) {
    this.pilot = _data.pilot
    this.flight = _data.flight;
    this.currentGroup = this.flight.group;
    this.groupsCount = this._data.groupsCount;
    this.editMode = _data.editMode;
    this.value = this.flight.order.toString();
    this.bestFlight = this._flightService.getBlankFlight(this.groupsCount);
    this.title = this._translate.instant("NewPlayer");
  }

  private _subscription;

  ngOnInit() {
    console.log(this.flight);
    if (!this.editMode)
      this._subscription = this._clockService.getReplayFrameEmitter()
        .subscribe(frame => {
          this.parseFrame(frame);
        })
    this.reloadBestFlight();
  }

  ngOnDestroy() {
    if (!this.editMode) {
      this._subscription.unsubscribe();
    }
    clearInterval(this.climbCounter);
    clearInterval(this.totalCounter);
  }

  /*---- COMPONENT ----*/
  reloadBestFlight() {
    this._flightService.getBestFlights(this.flight.roundNum, this._eventService.getEventId()).pipe(take(1)).subscribe(result => {
      if (result != null) {
        var rules = this._rulesService.getRules();
        switch (rules.bestFlightType) {
          case BestFlightType.Event: {
            this.bestFlight = result.bestFromEvent;
            break
          }
          case BestFlightType.Group: {
            this.bestFlight = result.bestFromGroups.filter(x => x != null).find(flight => flight.group == this.flight.group)
            break;
          }
          case BestFlightType.Round: {
            this.bestFlight = result.bestFromRound;
            break;
          }
        }
        if (!this.bestFlight) {
          this.bestFlight = this._flightService.getBlankFlight(this.groupsCount);
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

    if (!this.finished && !this.editMode && (this.flight.dns || this.flight.dnf))
      this._clockService.switchReplayFrameEmitter();

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
    this.reloadBestFlight();
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

  /*---- LOGIC ----*/

  winds = [];
  dirs = [];
  climbCounter: any;
  totalCounter: any;
  climbingTime = false;
  private parseFrame(frame: string) {
    if (frame == "0") return;
    var values = frame.split(';');
    values[0] = this._clockService.restoreFrame(values[0]);
    switch (values[0]) {
      case "$RNEW": {
        //component
        this.title = this._translate.instant("NewPlayer");
        this.value = this.flight.order.toString();

        clearInterval(this.climbCounter);
        clearInterval(this.totalCounter);
        this.climbingTime = false;
        this.winds = [];
        this.dirs = [];
        break;
      }
      case "$RCZP": {
        //component
        this.title = this._translate.instant("PrepTime");
        this.value = values[1] + " s";
        break;
      }
      case "$RTMO": {
        //component
        this.title = this._translate.instant("PrepTimeOver");
        this.value = "---";
        break;
      }
      case "$RCZS": {
        //component
        this.title = this._translate.instant("Starting time");
        this.value = values[1] + " s"

        if (!this.climbingTime) {
          this.climbingTime = true;
          this.climbCounter = setInterval(() => {
            if (this.climbingTime) {
              this.flight.sub1 = parseFloat((this.flight.sub1 + 0.1).toFixed(2));
            }
          }, 100)
        }

        //flight
        if (values[1] == "30") {
          this.flight.sub1 = 0;
          this.RCZS_timestamp = parseInt(values[2]);
        }
        break;
      }
      case "$RBAI": {
        var timestamp = parseInt(values[2]);
        this.flight.sub1 = this.round((timestamp - this.RCZS_timestamp) / 100.0);
        clearInterval(this.climbCounter);
        break;
      }
      case "$RNTR": {
        //component
        this.title = this._translate.instant("Total seconds");

        //flight
        var timestamp = parseInt(values[6]);

        var base = values[2];
        var time = this.round(parseFloat(values[3]) / 100.0);
        this.winds.push(values[4]);
        this.dirs.push(values[5]);

        switch (base) {
          case "0": {
            //component
            this.flight.dns = false;
            if (!this.started) {
              this.started = true;
              this.totalCounter = setInterval(() => {
                if (this.started)
                  this.value = (parseFloat(this.value) + 0.1).toFixed(1);
              }, 100)
            }

            //flight
            this.flight.sub1 = this.round((timestamp - this.RCZS_timestamp) / 100.0);
            if (this.flight.sub1 < 30.0)
              clearInterval(this.climbCounter);
            break;
          }
          case "1": {
            if (this.flight.sub1 >= 30.0)
              this.flight.sub2 = this.round(time - (this.flight.sub1 - 30));
            else
              this.flight.sub2 = this.round(time);
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
        this.flight.seconds = time;//this.round((timestamp - this.RCZS_timestamp) / 100.0);
        this.value = this.flight.seconds.toFixed(1).toString();
        break;
      }
      case "$REND": {
        //component
        this.title = this._translate.instant("Total seconds");
        this.finished = true;
        clearInterval(this.totalCounter);

        //flight
        var time = this.round(parseFloat(values[3]) / 100.0);
        this.flight.sub11 = this.round(time - this.flight.seconds);

        this.flight.seconds = time;//this.round((timestamp - this.RCZS_timestamp) / 100.0);
        this.value = this.flight.seconds.toFixed(1).toString();

        //this._subscription.unsubscribe();
        this._clockService.switchReplayFrameEmitter();
        break;
      }
      case "$RWSD": {
        let wind = parseFloat(values[3]) / 10.0;
        let dir = parseFloat(values[4]);

        this.winds.push(wind);
        this.dirs.push(dir);

        this.flight.windAvg = Math.round(this.winds.reduce((a, b) => a + b) / this.winds.length * 10.0) / 10.0;
        this.flight.dirAvg = Math.round(this.dirs.reduce((a, b) => a + b) / this.dirs.length * 10.0) / 10.0;
      }
    }
  }

  private switchEmitter() {
    this._subscription.unsubscribe();

    this._subscription = this._clockService.getReplayFrameEmitter()
      .subscribe(frame => {
        this.parseFrame(frame);
      })
  }

  private round(num: number) {
    return Math.round(num * 100) / 100;
  }

  private clearFlight(flight: Flight) {
    flight.seconds = 0;
    flight.sub1 = 0;
    flight.sub2 = 0;
    flight.sub3 = 0;
    flight.sub4 = 0;
    flight.sub5 = 0;
    flight.sub6 = 0;
    flight.sub7 = 0;
    flight.sub8 = 0;
    flight.sub9 = 0;
    flight.sub10 = 0;
    flight.sub11 = 0;
    flight.windAvg = 0;
    flight.dirAvg = 0;
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
        if (!this.finished && !this.editMode && (this.flight.dns || this.flight.dnf))
          this._clockService.switchReplayFrameEmitter();
        this.dialogRef.close(result)
      }
    })
  }

  isConnectedColor() {
    if (this._clockService.isConnected())
      return 'green';
    else
      return 'darkred';
  }
}

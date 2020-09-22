import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Flight } from '../models/flight';
import { Pilot } from '../models/pilot';
import { ClockService } from '../services/clock.service';

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
  groupsCount: number;
  currentGroup: string;
  groups: string[] = ["A", "B", "C", "D", "E"];

  title = "New player"
  value: string;

  constructor(public dialog: MatDialog,
    public dialogRef: MatDialogRef<PlayerComponent>,
    @Inject(MAT_DIALOG_DATA) private _data: PlayerDialogData,
    private _clockService: ClockService
  ) {
    this.pilot = _data.pilot
    this.flight = _data.flight;
    this.currentGroup = this.flight.group;
    this.groupsCount = this._data.groupsCount;
    this.editMode = _data.editMode;
    this.value = this.flight.order.toString();
  }

  private _subscription;
  ngOnInit() {
    this._subscription = this._clockService.getFrame()
      .subscribe(frame => {
        console.log(frame);
        this.parseFrame(frame);
      })
  }

  ngOnDestroy() {
    this._subscription.unsubscribe();
  }

  parseFrame(frame: string) {
    if (frame == "0") return;
    var values = frame.split(';');
    switch (values[0]) {
      case "$RCZP": {
        this.title = "CZAS PRZYGOTOWAWCZY";
        this.value = values[1] + " s";
        break;
      }
      case "$RTMO":{
        this.value = "PRZEKROCZONO";
        break;
      }
      case "$RCZS": {
        this.title = "CZAS STARTOWY";
        this.value = values[1] + " s"
        break;
      }
      case "$RNTR": {
        this.title = "CAŁKOWITY CZAS"
        this.value = "-";
        var base = values[2];
        var time = parseFloat(values[3])/100.0;
        
        switch(base){
          case "1":{
            this.flight.sub1 = time;
            break;
          }
          case "2":{
            this.flight.sub2 = time;
            break;
          }
          case "3":{
            this.flight.sub3 = time;
            break;
          }
          case "4":{
            this.flight.sub4 = time;
            break;
          }
          case "5":{
            this.flight.sub5 = time;
            break;
          }
          case "6":{
            this.flight.sub6 = time;
            break;
          }
          case "7":{
            this.flight.sub7 = time;
            break;
          }
          case "8":{
            this.flight.sub8 = time;
            break;
          }
          case "9":{
            this.flight.sub9 = time;
            break;
          }
        }

        break;
      }
      case "$REND": {
        this.title = "CAŁKOWITY CZAS"
        this.value = "-";
        var time = parseFloat(values[3])/100.0;
        this.flight.sub10 = time;
        break;
      }
    }
  }

  didNotFinish() {
    if (this.flight.dnf) {
      this.flight.dnf = false;
    } else {
      this.flight.dnf = true;
      this.flight.dns = false;
    }
  }

  didNotStart() {
    if (this.flight.dns) {
      this.flight.dns = false;
    } else {
      this.flight.dns = true;
      this.flight.dnf = false;
    }
  }
  saveFlight() {
    this.resolveConfirmationDialog().subscribe(confirmed => {
      if (confirmed) {
        this.flight.seconds=2;
        this.flight.pilotId = this.pilot.pilotId;
        this.flight.eventId = this.pilot.eventId;
        this.dialogRef.close(this.flight);
        
      }
    })
  }

  changeGroup(group: string) {
    this.currentGroup = this.flight.group = group;
  }

  /*---- DIALOG METHODS ----*/
  resolveConfirmationDialog() {
    return this.dialog.open(ConfirmDialogComponent, {
      width: '80%',
      maxWidth: '500px',
      disableClose: true
    }).afterClosed();
  }

  closeThisDialog(result?) {
    this.dialogRef.close(result)
  }
}

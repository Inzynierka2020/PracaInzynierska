import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Flight } from '../models/flight';
import { Pilot } from '../models/pilot';

class PlayerDialogData {
  pilot: Pilot
  flight: Flight
  groupsCount: number
}

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {

  @Input()
  editMode = true;
  @Input()
  returnDirectly = false;

  pilot: Pilot;
  flight: Flight;
  groupsCount: number;
  currentGroup: string;
  groups: string[] = ["A", "B", "C", "D", "E"];

  constructor(public dialog: MatDialog, public dialogRef: MatDialogRef<PlayerComponent>, @Inject(MAT_DIALOG_DATA) private _data: PlayerDialogData) {
    this.pilot = _data.pilot
    this.flight = _data.flight;
    this.currentGroup = this.flight.group;
    this.groupsCount = this._data.groupsCount;
  }

  ngOnInit() {
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
        this.editMode = false;
        this.flight.pilotId = this.pilot.pilotId;
        this.flight.eventId = this.pilot.eventId;
        this.dialogRef.close(this.flight);
      }
    })
  }

  switchEditMode() {
    this.resolveConfirmationDialog().subscribe(confirmed => {
      if (confirmed) {
        this.returnDirectly ? this.closeThisDialog() : this.editMode = !this.editMode;
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

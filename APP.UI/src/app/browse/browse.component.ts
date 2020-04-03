import { Component, OnInit, Input, Pipe, PipeTransform, EventEmitter, Output } from '@angular/core';
import { RoundsService } from '../services/rounds.service';
import { Pilot } from '../models/pilot';
import { Flight } from '../models/flight';
import { FlightsService } from '../services/flights.service';
import { PilotService } from '../services/pilot.service';
import { Round } from '../models/round';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})
export class BrowseComponent {

  @Input()
  round: Round;

  @Output()
  roundCanceled = new EventEmitter();

  dataSource: Pilot[];
  editMode = false;
  group = "A";
  isRoundCanceled = false;

  constructor(private _pilotService: PilotService, private dialog: MatDialog) {
    this._pilotService.getPilots().subscribe(pilotsResult => {
      this.dataSource = pilotsResult;
      this.ngOnChanges();
    });
  }

  ngOnChanges() {
    if (this.dataSource) {
      if (this.round) {
        console.log("round changed", this.round);
        this.dataSource.forEach(pilot => {
          pilot.flight = this.round.flights.find(flight => flight.pilotId == pilot.id);
        });
        this.isRoundCanceled = this.round.cancelled;
        console.log(this.isRoundCanceled);
        this.dataSource.sort((a, b) => a.flight.score > b.flight.score ? -1 : 1);
      }
    }

  }
  cancelRound() {
    this.resolveConfirmDialog().subscribe(confirmedResult => {
      console.log("CONF", confirmedResult)
      if (confirmedResult) {
        this.round.cancelled = !this.round.cancelled;
        this.roundCanceled.emit(this.round.cancelled);
      } else {
        this.isRoundCanceled = !this.isRoundCanceled;
      }
    })
  }

  changeGroup(group) {
    this.group = group;
  }

  changeMode() {
    this.editMode = !this.editMode;
  }

  /**** DIALOGS ****/

  private resolveConfirmDialog() {
    return this.dialog.open(ConfirmDialogComponent, {
      width: '80%',
      maxWidth: '500px',
      disableClose: true
    }).afterClosed();
  }
}


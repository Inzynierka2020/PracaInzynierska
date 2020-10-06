import { Component, OnInit, Input, Pipe, PipeTransform, EventEmitter, Output } from '@angular/core';
import { RoundsService } from '../services/rounds.service';
import { Pilot } from '../models/pilot';
import { Flight } from '../models/flight';
import { FlightsService } from '../services/flights.service';
import { PilotService } from '../services/pilot.service';
import { Round } from '../models/round';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { EventService } from '../services/event.service';
import { PlayerComponent } from '../player/player.component';

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

  constructor(private _pilotService: PilotService, private _eventService: EventService, private dialog: MatDialog, private _flightService: FlightsService, private _roundsService: RoundsService) {
    let eventId = _eventService.getEventId();
    this._pilotService.getPilots(eventId).subscribe(pilotsResult => {
      this.dataSource = pilotsResult;
      this.ngOnChanges();
    });
  }

  ngOnChanges() {
    if (this.dataSource) {
      if (this.round) {
        this.dataSource.forEach(pilot => {
          pilot.flight = this.round.flights.find(flight => flight.pilotId == pilot.pilotId);
          if (!pilot.flight)
            pilot.flight = this._flightService.getBlankData();
        });
        this.isRoundCanceled = this.round.cancelled;
        this.dataSource.sort((a, b) => a.flight.score < b.flight.score ? 1 : -1);
      }
    }

  }

  editFlight(pilot: Pilot) {
    if (!this.isRoundCanceled)
      this.resolvePlayerDialog(pilot, pilot.flight).subscribe(flightResult => {
        if (flightResult)
          this._flightService.saveFlight(flightResult).subscribe(result => {
            this._flightService.synchronizeFlight(flightResult.eventId, flightResult.pilotId, flightResult.roundNum).subscribe(result=>{
              this.roundCanceled.emit(this.round.cancelled);
            })
          })
      })
  }

  private resolvePlayerDialog(pilot: Pilot, flight: Flight) {
    var dialogRef = this.dialog.open(PlayerComponent, {
      width: '90%',
      maxWidth: '800px',
      height: '95%',
      maxHeight: '1000px',
      disableClose: true,
      data: {
        pilot,
        flight,
        groupsCount: this.round.numberOfGroups,
        editMode: true
      }
    })
    dialogRef.componentInstance.returnDirectly = true;

    return dialogRef.afterClosed();
  }

  cancelRound() {
    let opt = this.isRoundCanceled ? "CancelMsg.Reactivate" : "CancelMsg.Cancel";
    this.resolveConfirmDialog(opt).subscribe(confirmedResult => {
      if (confirmedResult) {
        this.round.cancelled = !this.round.cancelled;
        this.roundCanceled.emit(this.round.cancelled);
      } else {
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

  private resolveConfirmDialog(msg = null) {
    return this.dialog.open(ConfirmDialogComponent, {
      width: '80%',
      maxWidth: '500px',
      disableClose: true,
      data: msg
    }).afterClosed();
  }
}


import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { RoundsService } from '../services/rounds.service';
import { Round } from '../models/round';
import { Pilot } from '../models/pilot';
import { Flight } from '../models/flight';
import { EventService } from '../services/event.service';
import { FlightsService } from '../services/flights.service';
import { PilotService } from '../services/pilot.service';
import { PlayerComponent } from '../player/player.component';

@Component({
  selector: 'app-round',
  templateUrl: './round.component.html',
  styleUrls: ['./round.component.css']
})
export class RoundComponent {

  @Input()
  roundNumber: number;
  @Input()
  groupCount: number;

  @Output()
  finished = new EventEmitter<boolean>();

  canceled = false;
  eventId: number;
  flights: Flight[] = [];
  pilotsLeft: Pilot[] = [];
  pilotsFinished: Pilot[] = [];
  order = 1;

  noMorePilotsLeft = false;

  constructor(public dialog: MatDialog, private _roundsService: RoundsService,
    private _eventService: EventService, private _flighsService: FlightsService, private _pilotsService: PilotService) {
    this.eventId = _eventService.eventId;
    this._pilotsService.getPilots().subscribe(result => {
      this.pilotsLeft = result;
    });
  }

  /*---- METHODS ----*/

  createFlight(pilot: Pilot) {
    var flight = this._flighsService.getFlightData();
    flight.roundNum = this.roundNumber;

    this.resolvePlayerDialog(pilot, flight).subscribe(flightResult => {
      console.log(flightResult);
      if (flightResult)
        this.finishFlight(flightResult);
    })
  }

  finishFlight(flight: Flight) {
    flight.order = this.order++;
    this.flights.push(flight);

    var index = this.pilotsLeft.findIndex(pilot => pilot.id == flight.pilotId);
    var pilot = this.pilotsLeft[index];

    pilot.flight = flight;
    this.pilotsLeft.splice(index, 1);
    this.pilotsFinished.push(pilot);
    // this.pilotsFinished.sort((a, b) => a.flight.group.localeCompare(b.flight.group));

    this._flighsService.saveFlight(flight).subscribe(result => {
      this.updateScore();
    })

    if (this.pilotsLeft.length == 0) {
      this.noMorePilotsLeft = true;
    }
  }

  reflight(pilot: Pilot) {
    this.resolveConfirmDialog(`Do you want to reflight pilot: ${pilot.lastName.toUpperCase()} ${pilot.firstName}?`).subscribe(confirmResult => {
      if (confirmResult == true) {
        var index = this.pilotsFinished.findIndex(pilotToFind => pilotToFind.id == pilot.id);
        var pilotToReflight = this.pilotsFinished[index];
        pilotToReflight.flight = this._flighsService.getBlankData();
        pilotToReflight.flight.pilotId = pilotToReflight.id;
        pilotToReflight.flight.roundNum = this.roundNumber;
        pilotToReflight.flight.eventId = this.eventId;

        this.pilotsFinished.splice(index, 1);
        this.pilotsLeft.push(pilot);

        this.noMorePilotsLeft = false;
        this._flighsService.saveFlight(pilotToReflight.flight).subscribe(result => {
          this.updateScore();
        })
      }
    });
  }

  fillBlankFlights() {
    var count = this.pilotsLeft.length
    for (var _i = 0; _i < count; _i++) {
      var flight = this._flighsService.getBlankData();
      flight.pilotId = this.pilotsLeft[0].id;
      flight.eventId = this.eventId;
      flight.roundNum = this.roundNumber;
      this.finishFlight(flight);
    }
  }

  updateScore() {
    this._roundsService.updateRound(this.roundNumber).subscribe(result => {
      this._flighsService.getFinishedFlights(this.roundNumber).subscribe(flightsResult => {
        this.flights = flightsResult;
        this.pilotsFinished.forEach(pilot => {
          pilot.flight = this.flights.find(flight => flight.pilotId == pilot.id);
        });
        this.pilotsFinished.sort((a, b) => a.flight.score < b.flight.score ? 1 : -1);
      });
    });
  }

  cancelRound() {
    this.resolveConfirmDialog().subscribe(confirmResult => {
      if (confirmResult == true) {
        if (this.canceled) {
          this._roundsService.cancelRound(this.roundNumber).subscribe(result => { })
        } else {
          this._roundsService.uncancelRound(this.roundNumber).subscribe(result => { })
        }
      } else {
        this.canceled = !this.canceled;
      }
    });
  }

  finishRound() {
    this.resolveConfirmDialog().subscribe(confirmed => {
      if (confirmed) {
        if (this.pilotsLeft.length > 0) {
          this.fillBlankFlights();
        }
        this.finished.emit(true);
        this._roundsService.finishRound(this.roundNumber).subscribe(result => { });
      }
    });
  }

  /*---- DIALOGS ----*/

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
        groupsCount: this.groupCount
      }
    })
    dialogRef.componentInstance.returnDirectly = true;

    return dialogRef.afterClosed();
  }
  // if (this.mode == "browse") {
  //   dialogRef.componentInstance.editMode = false;
  // }

  private resolveConfirmDialog(data = null) {
    return this.dialog.open(ConfirmDialogComponent, {
      width: '80%',
      maxWidth: '500px',
      disableClose: true,
      data: data
    }).afterClosed();
  }
}

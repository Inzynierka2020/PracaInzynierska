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
    this.eventId = _eventService.getEventId();
    this._pilotsService.getPilots(this.eventId).subscribe(result => {
      this.pilotsLeft = result;
      this._flighsService.getFinishedFlights(this.roundNumber, this.eventId).subscribe(
        result => {
          result.forEach(flight => {
            this.finishFlight(flight);
          });
        },
        error => {
          console.log("INFO: Empty round");
        }
      )
    });
  }

  /*---- METHODS ----*/

  createFlight(pilot: Pilot) {
    var flight = this._flighsService.getBlankData();
    flight.roundNum = this.roundNumber;

    this.resolvePlayerDialog(pilot, flight, false).subscribe(flightResult => {
      if (flightResult)
        this.finishFlight(flightResult);
    })
  }

  editFlight(pilot: Pilot) {
    this.resolvePlayerDialog(pilot, pilot.flight, true).subscribe(flightResult => {
      if (flightResult)
        this._flighsService.saveFlight(flightResult).subscribe(result => {
          this.updateScore();
        })
    })
  }

  finishFlight(flight: Flight) {
    if(flight.seconds == 0){
      this._flighsService.saveFlight(flight).subscribe();
      return;
    }

    flight.order = this.order++;
    this.flights.push(flight);

    var index = this.pilotsLeft.findIndex(pilot => pilot.pilotId == flight.pilotId);
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
        var index = this.pilotsFinished.findIndex(pilotToFind => pilotToFind.pilotId == pilot.pilotId);
        var pilotToReflight = this.pilotsFinished[index];
        pilotToReflight.flight = this._flighsService.getBlankData();
        pilotToReflight.flight.pilotId = pilotToReflight.pilotId;
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
      flight.pilotId = this.pilotsLeft[_i].pilotId;
      flight.eventId = this.eventId;
      flight.roundNum = this.roundNumber;
      this.finishFlight(flight);
    }
  }

  updateScore() {
    this._roundsService.updateRound(this.roundNumber, this.eventId).subscribe(result => {
      this._flighsService.getFinishedFlights(this.roundNumber, this.eventId).subscribe(flightsResult => {
        this.flights = flightsResult;
        this.pilotsFinished.forEach(pilot => {
          pilot.flight = this.flights.find(flight => flight.pilotId == pilot.pilotId);
        });
        this.pilotsFinished.sort((a, b) => a.flight.score < b.flight.score ? 1 : -1);
      });
    });
  }

  cancelGroup(group: string) {
    let groupToCancel = this.flights.filter(flight => flight.group == group);
    groupToCancel.forEach(flight => {
      let pilotToCancel = this.pilotsFinished.find(pilot => pilot.pilotId == flight.pilotId);
      let pilotToCancelIndex = this.pilotsFinished.findIndex(pilot => pilot.pilotId == flight.pilotId);
      this.pilotsLeft.push(pilotToCancel);
      this.pilotsFinished.splice(pilotToCancelIndex, 1);
      //remove flight
    });
    this.noMorePilotsLeft = false;
  }

  cancelRound() {
    this.resolveConfirmDialog().subscribe(confirmResult => {
      if (confirmResult == true) {
        this.canceled = !this.canceled;
        if (this.canceled) {
          this._roundsService.cancelRound(this.roundNumber, this.eventId).subscribe(result => { })
        } else {
          this._roundsService.reactivateRound(this.roundNumber, this.eventId).subscribe(result => { })
        }
      } else {
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
        this._roundsService.finishRound(this.roundNumber, this.eventId).subscribe(result => { });
      }
    });
  }

  /*---- DIALOGS ----*/

  private resolvePlayerDialog(pilot: Pilot, flight: Flight, editMode: boolean) {
    var dialogRef = this.dialog.open(PlayerComponent, {
      width: '90%',
      maxWidth: '800px',
      height: '95%',
      maxHeight: '1000px',
      disableClose: true,
      data: {
        pilot,
        flight,
        groupsCount: this.groupCount,
        editMode: editMode
      }
    })
    dialogRef.componentInstance.returnDirectly = true;

    return dialogRef.afterClosed();
  }

  private resolveConfirmDialog(data = null) {
    return this.dialog.open(ConfirmDialogComponent, {
      width: '80%',
      maxWidth: '500px',
      disableClose: true,
      data: data
    }).afterClosed();
  }
}

import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { RoundsService } from '../services/rounds.service';
import { Pilot } from '../models/pilot';
import { Flight } from '../models/flight';
import { EventService } from '../services/event.service';
import { FlightsService } from '../services/flights.service';
import { PilotService } from '../services/pilot.service';
import { PlayerComponent } from '../player/player.component';
import { TranslateService } from '@ngx-translate/core';
import { Observable, Subject } from 'rxjs';

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
    private _eventService: EventService, private _flighsService: FlightsService, private _pilotsService: PilotService, private _roundService: RoundsService,
    private _translateService: TranslateService) {
    this.eventId = _eventService.getEventId();
    this._pilotsService.getPilots(this.eventId).subscribe(result => {
      this.pilotsLeft = result;
      this._flighsService.getFinishedFlights(this.roundNumber, this.eventId).subscribe(
        result => {
          this._roundService.reactivateRound(this.roundNumber, this.eventId);
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

  ngOnInit() {
    this._roundService.reactivateRound(this.roundNumber, this.eventId).subscribe();
  }

  /*---- METHODS ----*/

  createFlight(pilot: Pilot) {
    var flight = this._flighsService.getBlankData(this.groupCount);
    flight.roundNum = this.roundNumber;

    this.resolvePlayerDialog(pilot, flight, false).subscribe(flightResult => {
      if (flightResult)
        this.finishFlight(flightResult);
    })
  }

  saveFlight(flight: Flight, updateScore = true): Observable<boolean> {
    var emitter = new Subject<boolean>();
    this._flighsService.saveFlight(flight).subscribe(result => {
      this._flighsService.synchronizeFlight(flight.eventId, flight.pilotId, flight.roundNum).subscribe(
        result => console.log("INFO: FLIGHT SYNCHRONIZED"),
        error => console.log("ERROR: FLIGHT NOT SYNCHRONIZED", error))
        .add(() => {
          if (updateScore)
            this.updateScore()

          emitter.next(true)
        });
    })
    return emitter;
  }

  editFlight(pilot: Pilot) {
    this.resolvePlayerDialog(pilot, pilot.flight, true).subscribe(flightResult => {
      if (flightResult)
        this.saveFlight(flightResult).subscribe();
    })
  }

  finishFlight(flight: Flight) {
    flight.order = this.order++;
    this.flights.push(flight);

    var index = this.pilotsLeft.findIndex(pilot => pilot.pilotId == flight.pilotId);
    var pilot = this.pilotsLeft[index];

    pilot.flight = flight;
    this.pilotsLeft.splice(index, 1);
    this.pilotsFinished.push(pilot);
    // this.pilotsFinished.sort((a, b) => a.flight.group.localeCompare(b.flight.group));

    this.saveFlight(flight).subscribe(result => {
      if (this.pilotsLeft.length == 0) {
        this.noMorePilotsLeft = true;
      }
    });
  }

  reflight(pilot: Pilot) {
    var msg = this._translateService.instant("Reflight");
    this.resolveConfirmDialog(`${msg} ${pilot.lastName.toUpperCase()} ${pilot.firstName}?`).subscribe(confirmResult => {
      if (confirmResult == true) {
        this.reflightPilot(pilot);
      }
    });
  }

  private reflightPilot(pilot: Pilot) {
    var index = this.pilotsFinished.findIndex(pilotToFind => pilotToFind.pilotId == pilot.pilotId);
    var pilotToReflight = this.pilotsFinished[index];
    pilotToReflight.flight = this._flighsService.getBlankData(this.groupCount);
    pilotToReflight.flight.pilotId = pilotToReflight.pilotId;
    pilotToReflight.flight.roundNum = this.roundNumber;
    pilotToReflight.flight.eventId = this.eventId;

    this.pilotsFinished.splice(index, 1);
    this.pilotsLeft.push(pilot);

    this.noMorePilotsLeft = false;
    this._flighsService.deleteFlight(pilotToReflight.flight).subscribe(result => {
      if (this.pilotsFinished.length > 0)
        this.updateScore();
    })
  }

  fillBlankFlights(): Observable<boolean> {
    var emitter = new Subject<boolean>();
    var count = this.pilotsLeft.length
    var c = count;
    for (var _i = 0; _i < count; _i++) {
      var flight = this._flighsService.getBlankData(this.groupCount);
      flight.pilotId = this.pilotsLeft[_i].pilotId;
      flight.eventId = this.eventId;
      flight.roundNum = this.roundNumber;
      this.saveFlight(flight, false).subscribe(result => {
        c--;
        emitter.next(c == 0);
      });
    }
    setTimeout(() => {
      emitter.next(c == 0);
    }, 500);
    return emitter;
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
    var msg = this._translateService.instant("CancelMsg.GroupCancel");
    this.resolveConfirmDialog(msg + group + " ?").subscribe(confirmResult => {
      if (confirmResult == true) {
        let groupToCancel = this.flights.filter(flight => flight.group == group);
        groupToCancel.forEach(flight => {
          let pilotToCancel = this.pilotsFinished.find(pilot => pilot.pilotId == flight.pilotId);
          this.reflightPilot(pilotToCancel);
        });
      }
    });
  }

  cancelRound() {
    var msg;
    if (!this.canceled)
      msg = this._translateService.instant("CancelMsg.Cancel");
    else
      msg = this._translateService.instant("CancelMsg.Reactivate");

    this.resolveConfirmDialog(msg).subscribe(confirmResult => {
      if (confirmResult == true) {
        this.canceled = !this.canceled;
        if (this.canceled) {
          this._roundsService.cancelRound(this.roundNumber, this.eventId).subscribe(result => { })
        } else {
          this._roundsService.reactivateRound(this.roundNumber, this.eventId).subscribe(result => { })
        }
      }
    });
  }

  finishRound() {
    this.resolveConfirmDialog().subscribe(confirmed => {
      if (confirmed)
        this.fillBlankFlights().subscribe(result => {
          if (result)
            this._roundsService.finishRound(this.roundNumber, this.eventId).subscribe(result => {
              this.finished.emit(true);
            });
        });
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

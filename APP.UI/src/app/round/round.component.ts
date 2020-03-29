import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { RoundsService } from '../services/rounds.service';
import { Round } from '../models/round';
import { Pilot } from '../models/pilot';
import { Flight } from '../models/flight';
import { EventService } from '../services/event.service';

@Component({
  selector: 'app-round',
  templateUrl: './round.component.html',
  styleUrls: ['./round.component.css']
})
export class RoundComponent implements OnInit {
  
  @Input()
  roundNumber: number;
  eventId:number;
  
  @Output()
  finished = new EventEmitter<boolean>();
  
  canceled = false;
  
  pilotsLeft: Pilot[];
  pilotsFinished: Pilot[];
  flights: Flight[];
  

  constructor(public dialog: MatDialog, private _roundsService: RoundsService, private _eventService: EventService) {
    this.eventId = _eventService.eventId;
   }
  
  ngOnInit() {
  }

  countScore(){

  }

  finishRound() {
    var dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '80%',
      maxWidth: '500px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.finished.emit(false);
        this._roundsService.finishRound(this.roundNumber).subscribe(result => { });
      }
    });
  }
}

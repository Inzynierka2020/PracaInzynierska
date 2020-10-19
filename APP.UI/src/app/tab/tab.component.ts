import { Component, OnInit, ViewEncapsulation, Input, EventEmitter } from '@angular/core';
import { MatTabChangeEvent, MatTabGroup, MatTabLabel } from '@angular/material/tabs';
import { MatDialog } from '@angular/material/dialog';
import { NewRoundDialogComponent } from '../new-round-dialog/new-round-dialog.component';
import { RoundsService } from '../services/rounds.service';
import { PilotService } from '../services/pilot.service';
import { Pilot } from '../models/pilot';
import { Round } from '../models/round';
import { EventService } from '../services/event.service';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';

enum TAB {
  GENERAL = 0,
  BROWSE = 1,
  ROUND = 2,
}

@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TabComponent {
  switch = false; //temporary workaround for a bug

  eventId: number;

  previousTabIndex = 0;

  dataSource: Pilot[];

  rounds: Round[];
  roundNumber: number;
  browsedRoundIndex = 0;
  browsedRound: Round;

  isRoundStarted = false;
  groupCount: number;
  newRoundNumber = 0;

  constructor(
    public dialog: MatDialog,
    private _pilotService: PilotService,
    private _eventService: EventService,
    private _roundsService: RoundsService
  ) {
    this.eventId = this._eventService.getEventId()
    this.refreshRounds();
  }

  isBrowsing = false;

  onChangeTab(tabChangeEvent: MatTabChangeEvent, tab: MatTabGroup) {
    // this.refreshRounds();
    this.isBrowsing = false;
    if (tabChangeEvent.index == TAB.BROWSE) {
      this.isBrowsing = true;
      this.previousTabIndex = tabChangeEvent.index;
    } else if (tabChangeEvent.index == TAB.ROUND && !this.isRoundStarted) {
      this.resolveNewRoundDialogComponent().afterClosed().subscribe(newRoundResult => {
        if (newRoundResult.started) {
          this.isRoundStarted = true;
          this.groupCount = newRoundResult.groupCount;
          this.newRoundNumber = newRoundResult.roundNumber;
          this.startNewRound(this.newRoundNumber, this.groupCount);
        } else {
          tab.selectedIndex = this.previousTabIndex;
          this.switch = !this.switch;
        }
      })
    } else {
      this.refreshScores(); // ???
      this.previousTabIndex = tabChangeEvent.index;
    }
  }

  /*---- SCORE ----*/

  refreshScores() {
    this._eventService.updateGeneralScore(this.eventId).pipe(take(1)).subscribe(result => {
      this._pilotService.getPilots(this.eventId).subscribe(result => {
        this.dataSource = result;
        this.dataSource.sort((a, b) => a.score > b.score ? -1 : 1);
      });
    }, error => {
      this._pilotService.getPilots(this.eventId).subscribe(result => {
        this.dataSource = result;
        this.dataSource.sort((a, b) => a.score > b.score ? -1 : 1);
      }
      )
    })
  }

  /*---- BROWSING ----*/

  nextRound(interval: number, tab: MatTabGroup) {
    if (this.isBrowsing)
      this.browsedRoundIndex += interval;
    if (this.browsedRoundIndex < 0)
      this.browsedRoundIndex = this.rounds.length - 1
    if (this.browsedRoundIndex == this.rounds.length)
      this.browsedRoundIndex = 0

    this.changeRound();
  }

  changeRound() {
    this.roundNumber = this.rounds[this.browsedRoundIndex].roundNum;
    this.browsedRound = this.rounds[this.browsedRoundIndex];

    if (!this.browsedRound.synchronized)
      this.syncRound(this.roundNumber, this.eventId).subscribe(result => {
        if (result) {
          this.browsedRound.synchronized = true;
        } else {
          this.browsedRound.synchronized = false;
        }
      });

    var lastRound = this.rounds[this.rounds.length - 1];
    if (!lastRound.finished) {
      this.isRoundStarted = true;
      this.newRoundNumber = lastRound.roundNum;
      this.groupCount = lastRound.numberOfGroups;
      this.rounds.pop();
    }
  }

  cancelRound(toCancel: boolean) {
    this.browsedRound.synchronized = false;
    if (toCancel) {
      this._roundsService.cancelRound(this.browsedRound.roundNum, this.eventId).pipe(take(1)).subscribe(result => {
        this._roundsService.syncRound(this.browsedRound.roundNum, this.eventId).pipe(take(1)).subscribe(
          result => {
          },
          error => {
          }).add(() => this.refreshRounds())
      })
    } else {
      this._roundsService.reactivateRound(this.browsedRound.roundNum, this.eventId).pipe(take(1)).subscribe(result => {
        this._roundsService.syncRound(this.browsedRound.roundNum, this.eventId).pipe(take(1)).subscribe(
          result => {
          },
          error => {
          }).add(() => this.refreshRounds())
      })
    }
  }

  refreshRounds() {
    this._roundsService.updateAllRounds(this.eventId).pipe(take(1)).subscribe(updateResult => {
      this._roundsService.getRounds(this.eventId).pipe(take(1)).subscribe(roundsResult => {
        this.rounds = roundsResult;
        this.rounds = this.rounds.sort((a, b) => a.roundNum > b.roundNum ? 1 : -1);
        this.changeRound();
        this.refreshScores();
      });
    });
  }

  /*---- ROUND ----*/

  startNewRound(roundNumber: number, numberOfGroups: number) {
    this._roundsService.startNewRound(roundNumber, numberOfGroups, this.eventId).subscribe(result => { });
  }

  syncRound(roundNumber: number, eventId: number): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this._roundsService.syncRound(roundNumber, eventId).pipe(take(1)).subscribe(
        result => {
          observer.next(result);
        }
      );
    });
  }

  finishRound(finished, tab: MatTabGroup) {
    this.syncRound(this.roundNumber, this.eventId).pipe(take(1)).subscribe(result => {
      this.isRoundStarted = !finished;
      tab.selectedIndex = 0;
      this.refreshRounds()
    });
  }

  /*---- DIALOGS ----*/

  resolveNewRoundDialogComponent() {
    let recentRoundNumber = Math.max.apply(Math, this.rounds.map(function (o) { return o.roundNum; }))
    return this.dialog.open(NewRoundDialogComponent, {
      width: '80%',
      maxWidth: '800px',
      height: 'fitcontent',
      disableClose: true,
      data: {
        roundNumber: recentRoundNumber + 1
      }
    });
  }
}

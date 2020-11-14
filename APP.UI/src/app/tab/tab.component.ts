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
import { SnackService } from '../services/snack.service';

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

  browsedRoundChange = new EventEmitter();
  $browsedRoundChange: Observable<any>;
  constructor(
    public dialog: MatDialog,
    private _pilotService: PilotService,
    private _eventService: EventService,
    private _roundsService: RoundsService,
    private _snackService: SnackService
  ) {
    this.eventId = this._eventService.getEventId()
    this.refreshScores();
    this.$browsedRoundChange = this.browsedRoundChange.asObservable();
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
      this.refreshScores(tabChangeEvent.index == TAB.GENERAL); // GENERAL SCORES
      this.previousTabIndex = tabChangeEvent.index;
    }
  }

  /*---- SCORE ----*/

  outOfService = false;
  spinning = false;

  refreshScores(general = false) {
    this.spinning = true;

    this._eventService.updateGeneralScore(this.eventId).pipe(take(1)).subscribe(result => {
      this._eventService.synchronizeWithVault(this.eventId).pipe(take(1)).subscribe(result => {
        this.outOfService = !result;
        if (result) {
          if (general)
            this._snackService.open("VAULT SYNCHRONIZED")
        }
        else this._snackService.open("VAULT NOT SYNCRONIZED");

        this._pilotService.getPilots(this.eventId).subscribe(result => {
          this.dataSource = result;
          this.dataSource.sort((a, b) => a.score > b.score ? -1 : 1);
          this.spinning = false;
        });
        this.refreshRounds().pipe(take(1)).subscribe();
      });
    });

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
    var lastRound = this.rounds[this.rounds.length - 1];
    console.log(lastRound);
    if (lastRound && !lastRound.finished) {
      this.isRoundStarted = true;
      this.newRoundNumber = lastRound.roundNum;
      this.groupCount = lastRound.numberOfGroups;
      this.rounds.pop();
      this.browsedRoundChange.emit(true);
    }

    if (this.rounds.length == 0) {
      this.browsedRound = null;
      this.roundNumber = -1;
    } else if (lastRound == null) {
      // this.newRoundNumber = 1;
      this.isRoundStarted = false;
      // this.groupCount = 1;
    }

    if (this.rounds.length > 0) {
      this.roundNumber = this.rounds[this.browsedRoundIndex].roundNum;
      this.browsedRound = this.rounds[this.browsedRoundIndex];

      if (!this.browsedRound.synchronized)
        this.syncRound(this.roundNumber, this.eventId).subscribe(result => {
          this.browsedRound.synchronized = result;
        });
    }
  }

  cancelRound(toCancel: boolean) {
    this.spinning = true;
    this.browsedRound.synchronized = false;
    if (toCancel) {
      this._roundsService.cancelRound(this.browsedRound.roundNum, this.eventId).pipe(take(1)).subscribe(result => {
        this._roundsService.syncRound(this.browsedRound.roundNum, this.eventId).pipe(take(1)).subscribe(
          result => {
            //this.browsedRound.synchronized = result;
          },
          error => {
          }).add(() => this.refreshRounds().pipe(take(1)).subscribe(result => this.spinning = false))
      })
    } else {
      this._roundsService.reactivateRound(this.browsedRound.roundNum, this.eventId).pipe(take(1)).subscribe(result => {
        this._roundsService.syncRound(this.browsedRound.roundNum, this.eventId).pipe(take(1)).subscribe(
          result => {
            //this.browsedRound.synchronized = result;
          },
          error => {
          }).add(() => this.refreshRounds().pipe(take(1)).subscribe(result => this.spinning = false))
      })
    }
  }

  refreshRounds(): Observable<any> {
    return new Observable(observer => {
      this.spinning = true;
      this._roundsService.updateAllRounds(this.eventId).pipe(take(1)).subscribe(updateResult => {
        this._roundsService.getRounds(this.eventId).pipe(take(1)).subscribe(roundsResult => {
          this.rounds = roundsResult;
          this.rounds = this.rounds.sort((a, b) => a.roundNum > b.roundNum ? 1 : -1);
          this.changeRound();
          observer.next(true);
        });
      });
    })
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
    this.syncRound(this.newRoundNumber, this.eventId).pipe(take(1)).subscribe(result => {
      this.isRoundStarted = !finished;
      tab.selectedIndex = 0;
      this.refreshRounds().pipe(take(1)).subscribe(result => this.spinning = false);
    });
  }

  /*---- DIALOGS ----*/

  resolveNewRoundDialogComponent() {
    let recentRoundNumber = Math.max.apply(Math, this.rounds.map(function (o) { return o.roundNum; }))
    if (this.rounds.length == 0)
      recentRoundNumber = 0;

    return this.dialog.open(NewRoundDialogComponent, {
      width: '80%',
      maxWidth: '800px',
      height: 'fitcontent',
      disableClose: true,
      data: {
        takenNumbers: this.rounds.map(function (o) { return o.roundNum; }),
        roundNumber: recentRoundNumber + 1
      }
    });
  }
}

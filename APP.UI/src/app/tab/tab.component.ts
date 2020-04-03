import { Component, OnInit, ViewEncapsulation, Input, EventEmitter } from '@angular/core';
import { MatTabChangeEvent, MatTabGroup, MatTabLabel } from '@angular/material/tabs';
import { MatDialog } from '@angular/material/dialog';
import { NewRoundDialogComponent } from '../new-round-dialog/new-round-dialog.component';
import { RoundsService } from '../services/rounds.service';
import { PilotService } from '../services/pilot.service';
import { Pilot } from '../models/pilot';
import { Round } from '../models/round';
import { EventService } from '../services/event.service';

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

  @Input()
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

  constructor(public dialog: MatDialog, private _roundsService: RoundsService, private _pilotService: PilotService, private _eventService: EventService) {
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
        console.log(newRoundResult);
        if (newRoundResult.started) {
          this.isRoundStarted = true;
          this.groupCount = newRoundResult.groupCount;
          this.newRoundNumber = newRoundResult.roundNumber;
          this.startNewRound(this.newRoundNumber);
        } else {
          tab.selectedIndex = this.previousTabIndex;
          this.switch = !this.switch;
        }
      })
    } else {
      this.refreshScores();
      this.previousTabIndex = tabChangeEvent.index;
    }
  }

  /*---- SCORE ----*/

  refreshScores() {
    this._eventService.updateScore().subscribe(result => {
      this._pilotService.getPilots().subscribe(result => {
        this.dataSource = result;
        this.dataSource.sort((a, b) => a.score > b.score ? -1 : 1);
      });
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
  }

  refreshRounds() {
    this._roundsService.updateAllRounds().subscribe(updateResult => {
      this._roundsService.getRounds().subscribe(roundsResult => {
        console.log(roundsResult);
        this.rounds = roundsResult;
        this.changeRound();
        this.refreshScores();
      });
    });
  }

  /*---- ROUND ----*/

  startNewRound(roundNumber: number) {
    this._roundsService.startNewRound(roundNumber, this.eventId).subscribe(result => { });
  }

  finishRound(finished, tab: MatTabGroup) {
    this.isRoundStarted = !finished;
    tab.selectedIndex = 0;
    this.refreshRounds();
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

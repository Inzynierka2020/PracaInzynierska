import { Component, OnInit, ViewEncapsulation, Input, EventEmitter } from '@angular/core';
import { MatTabChangeEvent, MatTabGroup, MatTabLabel } from '@angular/material/tabs';
import { Subject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { NewRoundDialogComponent } from '../new-round-dialog/new-round-dialog.component';
import { RoundsService } from '../services/rounds.service';
import { PilotService } from '../services/pilot.service';
import { Pilot } from '../models/pilot';
import { group } from '@angular/animations';
import { Round } from '../models/round';
import { MaxLengthValidator } from '@angular/forms';

@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TabComponent implements OnInit {
  switch = false; //temporary workaround for a bug

  @Input()
  eventId: number;

  dataSource: Pilot[];
  started = false;
  previousTabIndex = 0;
  browsing = false;

  roundNumber: number;
  browsedRoundIndex = 0;
  browsedRound: Round;
  groupCount: number;
  newRoundNumber = 0;
  rounds: Round[];

  constructor(public dialog: MatDialog, private _roundsService: RoundsService, private _pilotService: PilotService) {
    this.refreshRounds();
    this.getGeneralScoreData();
  }

  ngOnInit() {
  }

  getGeneralScoreData() {
    this._pilotService.getPilots().subscribe(result => this.dataSource = result);
  }

  refreshRounds(){
    this._roundsService.getRounds().subscribe(roundsResult => {
      console.log(roundsResult);
      this.rounds = roundsResult;
      this.changeRound();
    })
  }


  onChangeTab(event: MatTabChangeEvent, tab: MatTabGroup) {
    if (event.index == 1) {
      //BROWSE TAB
      this.browsing = true;
      this.previousTabIndex = event.index;
    } else if (event.index == 2 && !this.started) {
      //NEW ROUND TAB
      this.browsing = false;
      let recentRoundNumber = Math.max.apply(Math, this.rounds.map(function(o) { return o.roundNum; }))
      const dialogRef = this.dialog.open(NewRoundDialogComponent, {
        width: '80%',
        maxWidth: '800px',
        height: 'fitcontent',
        disableClose: true,
        data: {
          roundNumber: recentRoundNumber+1
        }
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log();
        if (result.started) {
          this.started = true;
          this.groupCount = result.groupCount;
          this.newRoundNumber = result.roundNumber;
          this.startNewRound(this.newRoundNumber);
        } else {
          tab.selectedIndex = this.previousTabIndex;
          this.switch = !this.switch;
        }
      })
    } else {
      //GENERAL SCORE TAB
      this.getGeneralScoreData();
      this.previousTabIndex = event.index;
      this.browsing = false;
    }
  }

  startNewRound(roundNumber: number) {
    this._roundsService.startNewRound(roundNumber, this.eventId).subscribe(result => { });
  }

  finishRound(finished, tab: MatTabGroup) {
    this.started = !finished;
    tab.selectedIndex = 0;
    this.refreshRounds();
  }

  /*---- BROWSING ----*/

  nextRound(jump: number) {
    if (this.browsing)
      this.browsedRoundIndex += jump;
    if (this.browsedRoundIndex < 0)
      this.browsedRoundIndex = this.rounds.length - 1
    if (this.browsedRoundIndex == this.rounds.length)
      this.browsedRoundIndex = 0

    this.changeRound();
  }

  changeRound(){
    this.roundNumber = this.rounds[this.browsedRoundIndex].roundNum;
    this.browsedRound = this.rounds[this.browsedRoundIndex];
  }
}

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatTabChangeEvent, MatTabGroup, MatTabLabel } from '@angular/material/tabs';
import { Subject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { NewRoundDialogComponent } from '../new-round-dialog/new-round-dialog.component';
import { RoundsService } from '../services/rounds.service';
import { PilotService } from '../services/pilot.service';
import { Pilot } from '../models/pilot';
import { group } from '@angular/animations';

@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TabComponent implements OnInit {

  dataSource: Pilot[];

  started = false;
  switch = false; //temporary workaround for a bug
  previousTabIndex = 0;
  browsing = false;
  roundNumber = 1;
  newRoundNumber = 1;
  groupCount: number;

  constructor(public dialog: MatDialog, private _roundsService: RoundsService, private _pilotService: PilotService) { }

  ngOnInit() {
    this.getGeneralScoreData();
    this.getBrowsingData();
  }

  getGeneralScoreData() {
    this._pilotService.getPilots().subscribe(result => this.dataSource = result);
  }

  getBrowsingData() {
    // this._roundsService
  }



  onChangeTab(event: MatTabChangeEvent, tab: MatTabGroup) {
    if (event.index == 1) { 
      //BROWSE TAB
      this.browsing = true;
      this.previousTabIndex = event.index;
    } else if (event.index == 2 && !this.started) {
      //NEW ROUND TAB
      this.browsing = false;
      const dialogRef = this.dialog.open(NewRoundDialogComponent, {
        width: '80%',
        maxWidth: '800px',
        height: 'fitcontent',
        disableClose: true
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log();
        if (result.started) {
          this.started = true;
          this.newRoundNumber=result.roundNumber;
          this.groupCount = result.groupCount;
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

  startNewRound(roundNumber:number) {
    this._roundsService.startNewRound(roundNumber).subscribe(result=>{});
  }

  finishRound(finished, tab: MatTabGroup) {
    this.started = !finished;
    tab.selectedIndex = 0;
  }


  nextRound() {
    if (this.browsing)
      this.roundNumber++;
  }

  prevRound() {
    if (this.browsing)
      this.roundNumber--;
    if (this.roundNumber < 0)
      this.roundNumber = 0;
  }
}

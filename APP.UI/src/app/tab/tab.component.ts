import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatTabChangeEvent, MatTabGroup, MatTabLabel } from '@angular/material/tabs';
import { Subject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { NewRoundDialogComponent } from '../new-round-dialog/new-round-dialog.component';

@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TabComponent implements OnInit {

  started = false;
  switch = false; //temporary workaround for a bug
  previousTabIndex = 0;
  browsing = false;
  roundNumber = 1;
  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  onChangeTab(event: MatTabChangeEvent, tab: MatTabGroup) {
    if (event.index == 2 && !this.started) {
      this.browsing = false;
      const dialogRef = this.dialog.open(NewRoundDialogComponent, {
        width: '75%',
        maxWidth: '800px',
        height: 'fitcontent',
        disableClose: true
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.started = true;
          this.createNewRound();
        } else {
          tab.selectedIndex = this.previousTabIndex;
          this.switch = !this.switch;
        }
      })
    } else if (event.index == 1) {
      this.browsing = true;
      this.previousTabIndex = event.index;
    } else {
      this.previousTabIndex = event.index;
      this.browsing = false;
    }
  }

  createNewRound() {

  }

  finishRound(event, tab: MatTabGroup) {
    this.started = event;
    tab.selectedIndex = 0;
  }


  nextRound() {
    if (this.browsing)
      this.roundNumber++;
  }

  prevRound() {
    if (this.browsing)
      this.roundNumber--;
      if(this.roundNumber<0)
        this.roundNumber=0;
  }
}

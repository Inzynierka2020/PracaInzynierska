import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatTabChangeEvent, MatTabGroup } from '@angular/material/tabs';
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

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  onChangeTab(event: MatTabChangeEvent, tab: MatTabGroup) {
    if (event.index == 2 && !this.started) {
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
          this.switch=!this.switch;
        }
      })
    }else{
      this.previousTabIndex=event.index;
    }
  }

  createNewRound() {

  }

  finishRound(event, tab: MatTabGroup) {
    this.started = event;
    tab.selectedIndex = 0;
  }

}
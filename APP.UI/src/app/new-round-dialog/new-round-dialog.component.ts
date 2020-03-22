import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-new-round-dialog',
  templateUrl: './new-round-dialog.component.html',
  styleUrls: ['./new-round-dialog.component.css']
})
export class NewRoundDialogComponent implements OnInit {

  groups=false;
  disabled=false;
  roundNumber = 1;
  groupsNumber = 1;

  constructor(public dialogRef: MatDialogRef<NewRoundDialogComponent>) { }

  ngOnInit() {
  }

  increment(){
    this.roundNumber++;
  }
  decrement(){
    this.roundNumber--;
  }

  incrementGroups(){
    this.groupsNumber++;
  }
  decrementGroups(){
    this.groupsNumber--;
  }

}
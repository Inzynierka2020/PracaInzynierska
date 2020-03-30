import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-new-round-dialog',
  templateUrl: './new-round-dialog.component.html',
  styleUrls: ['./new-round-dialog.component.css']
})
export class NewRoundDialogComponent implements OnInit {

  roundNumber = 1;
  groupsNumber = 1;

  constructor(public dialogRef: MatDialogRef<NewRoundDialogComponent>) { }

  ngOnInit() {
  }

  close(started:boolean){
    this.dialogRef.close({started: started, roundNumber: this.roundNumber, groupCount: this.groupsNumber});
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
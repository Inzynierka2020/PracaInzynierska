import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

class NewRoundDialogData {
  roundNumber: number;
}

@Component({
  selector: 'app-new-round-dialog',
  templateUrl: './new-round-dialog.component.html',
  styleUrls: ['./new-round-dialog.component.css']
})
export class NewRoundDialogComponent implements OnInit {

  roundNumber: number;
  groupsNumber = 1;

  constructor(public dialogRef: MatDialogRef<NewRoundDialogComponent>, @Inject(MAT_DIALOG_DATA) private _data: NewRoundDialogData) {
    this.roundNumber = _data.roundNumber;
  }

  ngOnInit() {
  }

  close(started: boolean) {
    this.dialogRef.close({ started: started, roundNumber: this.roundNumber, groupCount: this.groupsNumber });
  }

  increment() {
    this.roundNumber++;
  }
  decrement() {
    this.roundNumber--;
  }

  incrementGroups() {
    this.groupsNumber++;
  }
  decrementGroups() {
    this.groupsNumber--;
  }

}
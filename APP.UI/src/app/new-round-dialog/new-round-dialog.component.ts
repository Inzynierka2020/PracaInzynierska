import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

class NewRoundDialogData {
  takenNumbers: number[];
  roundNumber: number;
}

@Component({
  selector: 'app-new-round-dialog',
  templateUrl: './new-round-dialog.component.html',
  styleUrls: ['./new-round-dialog.component.css']
})
export class NewRoundDialogComponent implements OnInit {

  roundNumber: number;
  takenNumbers: number[];
  groupsNumber = 1;

  constructor(public dialogRef: MatDialogRef<NewRoundDialogComponent>, @Inject(MAT_DIALOG_DATA) private _data: NewRoundDialogData) {
    this.roundNumber = _data.roundNumber;
    this.takenNumbers = _data.takenNumbers;
    console.log(this.takenNumbers);
  }

  ngOnInit() {
  }

  close(started: boolean) {
    this.dialogRef.close({ started: started, roundNumber: this.roundNumber, groupCount: this.groupsNumber });
  }

  increment() {
    var number = this.roundNumber + 1;
    while (this.takenNumbers.includes(number)) {
      number += 1;
      console.log( Math.max(...this.takenNumbers))
      if (number > Math.max(...this.takenNumbers)) {
        this.roundNumber = number;
        return;
      }
    }
    this.roundNumber = number;
  }

  decrement() {
    var number = this.roundNumber - 1;
    while (this.takenNumbers.includes(number)) {
      number -= 1;
      if (number == 0) {
        return;
      }
    }
    this.roundNumber = number;
  }

  incrementGroups() {
    if (this.groupsNumber < 5)
      this.groupsNumber++;
  }
  decrementGroups() {
    if (this.groupsNumber > 1)
      this.groupsNumber--;
  }

}
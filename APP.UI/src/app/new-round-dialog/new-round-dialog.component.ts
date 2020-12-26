import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PilotService } from '../services/pilot.service';
import { RulesService } from '../services/rules.service';

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
  groupsThreshold = 1;

  constructor(public dialogRef: MatDialogRef<NewRoundDialogComponent>, @Inject(MAT_DIALOG_DATA) private _data: NewRoundDialogData,
    private _rulesService: RulesService, private _pilotService: PilotService) {
    this.roundNumber = _data.roundNumber;
    this.takenNumbers = _data.takenNumbers;
    this.groupsThreshold = Math.floor(this._pilotService.pilotCount / _rulesService.getRules().pilotInGroupCount);
  }

  ngOnInit() {
  }

  close(started: boolean) {
    this.dialogRef.close({ started: started, roundNumber: this.roundNumber, groupCount: this.groupsNumber });
  }

  increment() {
    if (this.canIncrement())
      while (this.takenNumbers.includes(++this.roundNumber)) {
      }
  }

  canIncrement() {
    return true;
  }

  decrement() {
    if (this.canDecrement()) {
      while (this.takenNumbers.includes(--this.roundNumber)) {
        return false;
      }
    }
  }

  canDecrement(): boolean {
    var number = this.roundNumber - 1;
    if (number == 0) return false;
    while (this.takenNumbers.includes(number)) {
      number -= 1;
      if (number == 0) {
        return false;
      }
    }
    return true;
  }

  incrementGroups() {
    if (this.groupsNumber < this.groupsThreshold)
      this.groupsNumber++;
  }
  decrementGroups() {
    if (this.groupsNumber > 1)
      this.groupsNumber--;
  }

}
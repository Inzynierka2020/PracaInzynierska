import { Component, OnInit, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PlayerComponent } from '../player/player.component';
import { Pilot } from '../models/pilot';

const pilotData: Pilot[] = [
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
  { number: 99, lastName: "Laskowski", firstName: "Aleksander", total: 99999.99, result: 1999.99, penalty: 999, discard1: 999.99, discard2: 999.99, status: "XXXXXXXXXX" },
];

@Component({
  selector: 'app-score',
  templateUrl: './score.component.html',
  styleUrls: ['./score.component.css']
})
export class ScoreComponent implements OnInit {

  dataSource = pilotData;

  @Input()
  mode = "";

  @Input()
  selectPlayers = true;

  @Input()
  group='A';

  @Input()
  title: string;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  openPlayer(event) {
    if (this.mode != "general") {
      var dialogRef = this.dialog.open(PlayerComponent, {
        width: '90%',
        maxWidth: '800px',
        disableClose: true,
      })
      if (this.mode == "browse") {
        dialogRef.componentInstance.editMode = false;
      }
      dialogRef.componentInstance.returnDirectly = this.mode!="browse";
    }
  }
}

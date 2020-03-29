import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PlayerComponent } from '../player/player.component';
import { Pilot } from '../models/pilot';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PilotService } from '../services/pilot.service';

@Component({
  selector: 'app-score',
  templateUrl: './score.component.html',
  styleUrls: ['./score.component.css']
})
export class ScoreComponent implements OnInit {

  @Input()
  dataSource: Pilot[];

  @Input()
  mode = "";


  constructor(public dialog: MatDialog  ) {
  }
  
  ngOnInit() {
     
  }

  openPlayer(event) {
    if (this.mode != "general") {
      var dialogRef = this.dialog.open(PlayerComponent, {
        width: '90%',
        maxWidth: '800px',
        height: '95%',
        maxHeight: '1000px',
        disableClose: true,
      })
      if (this.mode == "browse") {
        dialogRef.componentInstance.editMode = false;
      }
      dialogRef.componentInstance.returnDirectly = this.mode != "browse";
    }
  }

  getPilots() {

  }
}

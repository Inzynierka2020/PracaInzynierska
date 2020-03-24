import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PlayerComponent } from '../player/player.component';
import { Pilot } from '../models/pilot';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-score',
  templateUrl: './score.component.html',
  styleUrls: ['./score.component.css']
})
export class ScoreComponent implements OnInit {

  dataSource: Pilot[];

  @Input()
  mode = "";

  @Input()
  selectPlayers = true;

  @Input()
  group = 'A';

  @Input()
  title: string;

  constructor(public dialog: MatDialog, private _http: HttpClient, @Inject("BASE_URL") private _baseUrl: string ) {
  }
  
  ngOnInit() {
     this._http.get<Pilot[]>(this._baseUrl + 'pilots').subscribe(result=>this.dataSource=result);
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

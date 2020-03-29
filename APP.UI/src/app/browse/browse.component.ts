import { Component, OnInit, Input } from '@angular/core';
import { RoundsService } from '../services/rounds.service';
import { Pilot } from '../models/pilot';
import { Flight } from '../models/flight';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})
export class BrowseComponent implements OnInit {

  @Input()
  roundNumber = 0;

  pilots: Pilot[];
  flights: Flight[];

  dataSource: Pilot[];
  editMode=false;
  group='A';

  constructor(private _roundsService: RoundsService) { }

  ngOnInit() {
    this._roundsService.getRound(this.roundNumber).subscribe(result=>{
      this.dataSource=result;
    });
  }

  changeGroup(group){
    this.group=group;
  }

  changeMode(){
    this.editMode=!this.editMode;
  }
}

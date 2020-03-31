import { Component, OnInit, Input, Pipe, PipeTransform, EventEmitter } from '@angular/core';
import { RoundsService } from '../services/rounds.service';
import { Pilot } from '../models/pilot';
import { Flight } from '../models/flight';
import { FlightsService } from '../services/flights.service';
import { PilotService } from '../services/pilot.service';
import { Round } from '../models/round';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})
export class BrowseComponent implements OnInit {

  @Input()
  round: Round;

  dataSource: Pilot[];
  editMode = false;
  group = "A";

  constructor(private _pilotService: PilotService) {
    this._pilotService.getPilots().subscribe(pilotsResult => {
      this.dataSource = pilotsResult;
      this.ngOnChanges();
    });
  }

  ngOnInit() {
  }

  ngOnChanges() {
    if (this.dataSource) {
      if (this.round) {
        console.log("round changed", this.round);
        this.dataSource.forEach(pilot => {
          pilot.flight = this.round.flights.find(flight => flight.pilotId == pilot.id);
        });

        this.dataSource.sort((a, b) => a.flight.score > b.flight.score ? -1 : 1);
      }
    }
  }

  changeGroup(group) {
    this.group = group;
  }

  changeMode() {
    this.editMode = !this.editMode;
  }
}


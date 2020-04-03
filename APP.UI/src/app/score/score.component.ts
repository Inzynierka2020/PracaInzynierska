import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Pilot } from '../models/pilot';

@Component({
  selector: 'app-score',
  templateUrl: './score.component.html',
  styleUrls: ['./score.component.css']
})
export class ScoreComponent {

  @Input()
  dataSource: Pilot[];

  @Input()
  mode = "";

  @Output()
  rowAction: EventEmitter<Pilot> = new EventEmitter<Pilot>();

  callRowAction(pilot: Pilot) {
    this.rowAction.emit(pilot);
  }
}
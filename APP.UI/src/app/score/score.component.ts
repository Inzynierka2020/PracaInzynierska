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
  handlePan = new EventEmitter<any>();

  @Output()
  rowClickAction: EventEmitter<Pilot> = new EventEmitter<Pilot>();

  @Output()
  rowPressAction: EventEmitter<Pilot> = new EventEmitter<Pilot>();

  callRowAction(pilot: Pilot) {
    this.rowClickAction.emit(pilot);
  }

  callPressAction(pilot: Pilot) {
    this.rowPressAction.emit(pilot);
  }

  call(event) {
    this.handlePan.emit(event)
  }
}
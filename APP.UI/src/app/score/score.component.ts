import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Pilot } from '../models/pilot';

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

  @Output()
  rowAction: EventEmitter<Pilot> = new EventEmitter<Pilot>();

  constructor() { }

  ngOnInit() { }

  callRowAction(pilot: Pilot) {
    this.rowAction.emit(pilot);
  }
}
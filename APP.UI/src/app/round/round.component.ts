import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-round',
  templateUrl: './round.component.html',
  styleUrls: ['./round.component.css']
})
export class RoundComponent implements OnInit {
  @Output()
  finished = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit() {
  }

  finishRound(){
    this.finished.emit(false);
  }
}

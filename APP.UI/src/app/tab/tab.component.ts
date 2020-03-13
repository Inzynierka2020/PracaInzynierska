import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatTabChangeEvent, MatTabGroup } from '@angular/material/tabs';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TabComponent implements OnInit {
  started = false;

  constructor() { }

  ngOnInit() {
  }

  onChangeTab(event: MatTabChangeEvent) {
    if (event.index == 2) {
      this.started = true;
      this.createNewRound();
    }
  }

  createNewRound() {

  }

  finishRound(event, tab: MatTabGroup) {
    this.started = event;
    tab.selectedIndex=0;
  }

}
import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation } from '@angular/core';
import { Settings } from 'src/app/models/settings';

@Component({
  selector: 'app-connection-settings',
  templateUrl: './connection-settings.component.html',
  styleUrls: ['./connection-settings.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ConnectionSettingsComponent implements OnInit {


  @Input()
  settings: Settings;
  @Output()
  settingsChange = new EventEmitter<any>();

  constructor() { }

  disabled = false;
  ngOnInit() {
    let eventId = localStorage.getItem('eventId');
    if (eventId) {
      this.disabled = true;
    } else {
      this.disabled = false;
    }
  }
  ngOnChange() {
    this.settingsChange.emit(this.settings);
  }


}

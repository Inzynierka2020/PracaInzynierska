import { Component, Input, Output, EventEmitter } from '@angular/core';
import { EventService } from '../services/event.service';
import { Event } from '../models/event';
import { Settings } from '../models/settings';
import { ConfigService } from '../services/config.service';
import { SnackService } from '../services/snack.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent {

  loading = false;

  @Input()
  event: Event;
  @Output()
  eventChange = new EventEmitter();

  settings: Settings = {
    apiUrl: "http://www.f3xvault.com/api.php?",
    // login: "piotrek.adamczykk@gmail.com",
    login: "",
    // password: "ascroft",
    password: "",
    eventId: null
  }


  constructor(private _eventService: EventService,
    private _configService: ConfigService,
    private _snackService: SnackService) {

    this.loading = true;
    let eventId = localStorage.getItem('eventId');
    if (eventId) {
      this.settings.eventId = parseInt(eventId);
      this.getEvent();
    } else {
      this.loading = false;
    }
  }

  startEvent() {
    if (!this.settings.eventId) {
      this._snackService.open("NO EVENT ID PROVIDED.");
      return;
    }

    this.settings.eventId = parseInt(this.settings.eventId.toString());

    this.loading = true;
    localStorage.setItem('eventId', this.settings.eventId.toString());

    this._configService.updateConfig(this.settings).subscribe(result => {
      this._eventService.initializeEvent(this.settings.eventId).subscribe(result => {
        this.getEvent();
      }, error => {
        this.getEvent();
      })
    }, error => {
      this._snackService.open("CONNECTION LOST. CACHING PREVIOUS USER CONFIG");
      this.getEvent();
    });
  }

  getEvent() {
    this._eventService.getEvent(this.settings.eventId).subscribe(eventResult => {
      this.eventChange.emit(eventResult);
      this.loading = false;
    }, error => {
      localStorage.removeItem('eventId');
      this.loading = false;
      this._snackService.open("WRONG API SETTINGS OR NO CONNECTION");
    })
  }
}

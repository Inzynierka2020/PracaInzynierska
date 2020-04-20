import { Component, Input, Output, EventEmitter } from '@angular/core';
import { EventService } from '../services/event.service';
import { Event } from '../models/event';
import { Settings } from '../models/settings';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent {

  @Input()
  event: Event;
  @Output()
  eventChange = new EventEmitter();

  settings: Settings = {
    apiUrl: "http://www.f3xvault.com/api.php?",
    login: "piotrek.adamczykk@gmail.com",
    password: "ascroft",
    eventId: null
  }

  constructor(private _eventService: EventService) {
    let eventId = localStorage.getItem('eventId');
    if (eventId) {
      this.settings.eventId = Number(eventId);
      this.getEvent();
    }
  }

  startEvent() {
    localStorage.setItem('eventId', this.settings.eventId.toString());
    this._eventService.initializeEvent(this.settings.eventId).subscribe(result => {
      this.getEvent();
    }, error => {
      this.getEvent();
    })
  }

  getEvent() {
    this._eventService.getEvent(this.settings.eventId).subscribe(eventResult => {
      this.eventChange.emit(eventResult);
    }, error => {
      localStorage.removeItem('eventId');
      window.location.reload();
    })
  }
}

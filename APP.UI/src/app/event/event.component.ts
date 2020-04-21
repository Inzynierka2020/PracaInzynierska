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

  loading = false;

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
    this.loading = true;
    let eventId = localStorage.getItem('eventId');
    if (eventId) {
      this.settings.eventId = Number(eventId);
      this.getEvent();
    }else{
      this.loading=false;
    }
  }

  startEvent() {
    if(!this.settings.eventId){
      console.log("No EVENT ID provided");
      return;
    }
    this.loading = true;
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
      this.loading=false;
    }, error => {
      localStorage.removeItem('eventId');
      window.location.reload();
    })
  }
}

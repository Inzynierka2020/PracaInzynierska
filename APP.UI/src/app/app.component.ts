import { Component } from '@angular/core';
import { EventService } from './services/event.service';
import { Event } from '../app/models/event'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'App';

  event: Event = {
    eventId:0,
    eventEndDate: "",
    eventLocation: "",
    eventName: "---",
    eventStartDate: "",
    eventType: "",
    numberOfRounds: 0,
    started: false
  }

constructor(private eventService: EventService) {

}

}

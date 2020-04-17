import { Component } from '@angular/core';
import { Event } from '../app/models/event'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  event: Event;
  eventStarted = false;

  start() {
    if (event)
      this.eventStarted = true;
  }
}
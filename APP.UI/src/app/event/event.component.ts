import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { EventService } from '../services/event.service';
import { Router } from '@angular/router';
import { Event } from '../models/event';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  constructor(private _eventService: EventService, private _router: Router) { }

  @Input()
  event: Event;

  @Output()
  eventChange: EventEmitter<Event> = new EventEmitter<Event>();

  ngOnInit() {
  }

  startEvent() {
    this._eventService.initEvent(this.event.eventId).subscribe(result => {
      this._eventService.getEvent(this.event.eventId).subscribe(result => {
        result.started = true;
        this.eventChange.emit(result);
      })
    })
  }
}

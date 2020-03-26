import { Component, OnInit } from '@angular/core';
import { EventService } from '../services/event.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  constructor(private _eventService: EventService, private _router: Router) { }

  eventId: number;

  ngOnInit() {
  }


  startEvent() {
    // this._eventService.startEvent().subscribe(result => {
    // })
    this._router.navigate(['/tab']);
  }
}

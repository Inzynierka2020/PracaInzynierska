import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Event } from '../models/event';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }

  private eventId: number;

  initializeEvent(eventId: number): Observable<string> {
    return this._http.post<string>(this._baseUrl + "events/event-data/" + eventId, null, {
      responseType: 'text' as 'json'
    });
  }

  getEvent(eventId: number): Observable<Event> {
    this.eventId = eventId;
    return this._http.get<Event>(this._baseUrl + "events/" + eventId);
  }

  deleteEvent(eventId: number): Observable<any> {
    this.eventId = null;
    return this._http.delete(this._baseUrl + "events/" + eventId);
  }

  getEventId() {
    return this.eventId;
  }

  updateGeneralScore(eventId: number): Observable<any> {
    return this._http.put(this._baseUrl + "events/total-score/" + eventId, null);
  }
}

import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Event } from '../models/event';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }

  eventId:number;

  initEvent(eventId: number): Observable<string> {
    this.eventId=eventId;
    return this._http.post<string>(this._baseUrl + "events/event-data/" + eventId, null, {
      responseType: 'text' as 'json'
    });
  }

  deleteEvent(eventId: number): Observable<any> {
    return this._http.delete(this._baseUrl + "events/" + eventId);
  }

  // initPilots(eventId: number): Observable<string> {
  //   return this._http.post<string>(this._baseUrl + "events/download-pilots-data/" + eventId, null,{
  //     responseType: 'text' as 'json'
  //   });
  // }

  getEvent(eventId: number): Observable<Event> {
    return this._http.get<Event>(this._baseUrl + "events/" + eventId);
  }

  updateScore():Observable<any> {
    return this._http.put(this._baseUrl + "events/total-score", null);
  }
}

import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Pilot } from '../models/pilot';

@Injectable({
  providedIn: 'root'
})
export class PilotService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }

  getPilots(eventId: number) {
    return this._http.get<Pilot[]>(this._baseUrl + 'pilots?eventId='+eventId);
  }

  // getPilotsWithFlights(): Observable<Pilot[]>{
  //   return this._http.get<Pilot[]>(this._baseUrl + 'pilots');
  // }
}

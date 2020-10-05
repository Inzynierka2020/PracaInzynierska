import { Injectable, Inject } from '@angular/core';
import { Flight } from '../models/flight';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pilot } from '../models/pilot';

@Injectable({
  providedIn: 'root'
})
export class FlightsService {


  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }

  getFinishedFlights(roundNumber, eventId): Observable<Flight[]> {
    return this._http.get<Flight[]>(this._baseUrl + "rounds/" + roundNumber + "/flights?eventId="+eventId);
  }

  getBestFlightFromRound(roundNumber, eventId): Observable<Flight> {
    return this._http.get<Flight>(this._baseUrl + "rounds/best/" + roundNumber + "?eventId="+eventId);
  }

  saveFlight(flight: Flight): Observable<any> {
    return this._http.post<string>(this._baseUrl + "flights/", flight, {
      responseType: 'text' as 'json'
    });
  }

  deleteFlight(flight: Flight): Observable<any> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: flight
    };
    return this._http.delete<Flight>(this._baseUrl + "flights/delete", options);
  }

  synchronizeFlight(eventId, pilotId, roundNum): Observable<any>{
    return this._http.post<string>(this._baseUrl + "flights/vault"+ "?eventId="+eventId+"&pilotId="+pilotId+"&roundNum="+roundNum, null);
  }

  getFlightData(): Flight {
    var flight = <Flight>{
      eventPilotId: 0,
      eventRoundId: 0,
      pilotId: 0,
      roundNum: 0,
      eventId: 0,
      penalty: 0,
      order: 0,
      group: "",
      flightTime: "",
      windAvg: 0,
      dirAvg: 0,
      seconds: this.genRandomFloat() * this.genRandomFloat(),
      sub1: this.genRandomFloat(),
      sub2: this.genRandomFloat(),
      sub3: this.genRandomFloat(),
      sub4: this.genRandomFloat(),
      sub5: this.genRandomFloat(),
      sub6: this.genRandomFloat(),
      sub7: this.genRandomFloat(),
      sub8: this.genRandomFloat(),
      sub9: this.genRandomFloat(),
      sub10: this.genRandomFloat(),
      sub11: this.genRandomFloat(),
      dns: false,
      dnf: false,
      score: 0
    }
    return flight;
  }

  getBlankData(): Flight {
    var flight = <Flight>{
      eventPilotId: 0,
      eventRoundId: 0,
      pilotId: 0,
      roundNum: 0,
      eventId: 0,
      penalty: 0,
      order: 0,
      group: "",
      flightTime: "",
      windAvg: 0,
      dirAvg: 0,
      seconds: 0,
      sub1: 0,
      sub2: 0,
      sub3: 0,
      sub4: 0,
      sub5: 0,
      sub6: 0,
      sub7: 0,
      sub8: 0,
      sub9: 0,
      sub10: 0,
      sub11: 0,
      dns: false,
      dnf: false,
      score: 0
    }
    return flight;
  }

  private genRandomFloat() {
    return parseFloat((Math.random() * (0.5 - 4.0) + 4.0).toFixed(2));
  }
}

import { Injectable, Inject } from '@angular/core';
import { Flight } from '../models/flight';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FlightsService {
  

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }

  getFinishedFlights(roundNumber): Observable<Flight[]> {
    return this._http.get<Flight[]>(this._baseUrl + "pilots/" + roundNumber + "/finished-flights", {
      responseType: 'text' as 'json'
    });
  }

  saveFlight(flight: Flight): Observable<any> {
    return this._http.post<string>(this._baseUrl + "flights/", flight, {
      responseType: 'text' as 'json'
    });
  }

  getFlightData(): Flight {
    var flight = <Flight>{
      pilotId: 0,
      roundNum: 0,
      eventId: 0,
      penalty: 0,
      order: 0,
      group: "A",
      flightTime: "",
      windAvg: 0,
      dirAvg: 0,
      seconds: 0,
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

  private genRandomFloat() {
    return parseFloat((Math.random() * (0.5 - 4.0) + 4.0).toFixed(2));
  }
}

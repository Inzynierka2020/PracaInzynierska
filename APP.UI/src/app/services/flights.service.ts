import { Injectable, Inject } from '@angular/core';
import { Flight } from '../models/flight';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { IndexedDbService } from './indexed-db.service';
import { take } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FlightsService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl, private _dbService: IndexedDbService) { }

  getFinishedFlights(roundNumber, eventId): Observable<Flight[]> {
    if (!this._dbService.hasPriority())
      return new Observable<Flight[]>(observer => {
        this._http.get<Flight[]>(this._baseUrl + "rounds/" + roundNumber + "/flights?eventId=" + eventId).subscribe(
          result => {
            observer.next(result);
          },
          error => {
            this._dbService.setPriority(true);
            this._dbService.readFlightsFromRound(roundNumber, eventId).subscribe(flightsFromRounds => {
              observer.next(flightsFromRounds);
            });
          }
        )
      });
    else
      return new Observable<Flight[]>(observer => {
        this._dbService.readFlightsFromRound(roundNumber, eventId).subscribe(flightsFromRounds => {
          observer.next(flightsFromRounds);
        });
      })
  }

  getBestFlightFromRound(roundNumber, eventId): Observable<Flight> {
    if (!this._dbService.hasPriority())
      return new Observable<Flight>(observer => {
        this._http.get<Flight>(this._baseUrl + "rounds/best/" + roundNumber + "?eventId=" + eventId).subscribe(
          result => {
            observer.next(result);
          },
          error => {
            this._dbService.setPriority(true);
            this._dbService.readBestFlight(roundNumber, eventId).subscribe(bestFlight => {
              observer.next(bestFlight);
            });
          }
        )
      })
    else
      return new Observable<Flight>(observer => {
        this._dbService.readBestFlight(roundNumber, eventId).subscribe(bestFlight => {
          observer.next(bestFlight);
        });
      });
  }

  saveFlight(flight: Flight): Observable<boolean> {
    return new Observable<boolean>(observer => {
      if (!this._dbService.hasPriority())
        this._http.post<string>(this._baseUrl + "flights/", flight, {
          responseType: 'text' as 'json'
        }).subscribe(
          result => {
          }, error => {
            this._dbService.setPriority(true);
          }).add(() => {
            this._dbService.createFlight(flight).pipe(take(1)).subscribe(result => {
              observer.next(true);
            });
          });
      else {
        this._dbService.createFlight(flight).pipe(take(1)).subscribe(result => {
          observer.next(false);
        });
      }
    })
  }

  deleteFlight(flight: Flight): Observable<Boolean> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: flight
    };

    if (!this._dbService.hasPriority())
      return new Observable<boolean>(observer => {
        this._http.delete<Flight>(this._baseUrl + "flights/delete", options).subscribe(
          result => {
            observer.next(true);
          }, error => {
            observer.next(false);
            this._dbService.setPriority(true);
          }).add(() => this._dbService.deleteFlight(flight));
      })
    else {
      this._dbService.deleteFlight(flight)
      return of(false);
    }
  }

  synchronizeFlight(eventId, pilotId, roundNum): Observable<boolean> {
    if (!this._dbService.hasPriority())
      return new Observable<boolean>(observer => {
        this._http.post<string>(this._baseUrl + "flights/vault" + "?eventId=" + eventId + "&pilotId=" + pilotId + "&roundNum=" + roundNum, null).subscribe(
          result => {
            observer.next(true);
          },
          error => {
            this._dbService.setPriority(false);
            observer.next(false);
          }
        );
      })
    else return of(false);
  }

  getBlankFlight(groupCount: number): Flight {
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
      score: 0,
      synchronized: false
    }
    if (groupCount > 1) {
      flight.group = "A";
    }
    return flight;
  }
}

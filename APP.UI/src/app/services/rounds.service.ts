import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Round } from '../models/round';
import { IndexedDbService } from './indexed-db.service';
import { take } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RoundsService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl, private _dbService: IndexedDbService) { }

  updateRound(roundNumber: number, eventId: number): Observable<boolean> {
    if (!this._dbService.hasPriority())
      return new Observable<boolean>(observer => {
        this._http.put<any>(this._baseUrl + "rounds/update/" + roundNumber + "?eventId=" + eventId, {
          responseType: 'text' as 'json'
        }).subscribe(
          result => {
            observer.next(true);
          },
          error => {
            this._dbService.setPriority(true);
            observer.next(false);
          }
        );
      })
    else return of(false);
  }

  updateAllRounds(eventId: number): Observable<any> {
    if (!this._dbService.hasPriority())
      return new Observable<boolean>(observer => {
        this._http.put<any>(this._baseUrl + "rounds/update?eventId=" + eventId, {
          responseType: 'text' as 'json'
        }).subscribe(
          result => {
            observer.next(true);
          },
          error => {
            this._dbService.setPriority(true);
            observer.next(false);
          }
        );
      })
    else return of(false);
  }

  startNewRound(roundNumber: number, numberOfGroups: number, eventId: number): Observable<Round> {
    let round: Round = {
      cancelled: false,
      eventId: eventId,
      finished: false,
      roundNum: roundNumber,
      flights: [],
      numberOfGroups: numberOfGroups,
      synchronized: false
    }

    if (!this._dbService.hasPriority()) {
      return new Observable<Round>(observer => {
        this._http.post<Round>(this._baseUrl + "rounds/new?eventId=" + eventId + "&numberOfGroups=" + round.numberOfGroups + "&roundNum=" + round.roundNum,
          null, {
          responseType: 'text' as 'json'
        }).subscribe(
          res => { },
          error => this._dbService.setPriority(true))
          .add(() => {
            this._dbService.createRound(round).pipe(take(1)).subscribe(result => {
              observer.next(round);
            });
          });
      })
    } else {
      return new Observable<Round>(observer => {
        this._dbService.createRound(round).pipe(take(1)).subscribe(result => {
          observer.next(round);
        });
      });
    }
  }

  finishRound(roundNumber: number, eventId: number): Observable<boolean> {
    if (!this._dbService.hasPriority())
      return new Observable<boolean>(observer => {
        this._http.put<any>(this._baseUrl + "rounds/finish/" + roundNumber + "?eventId=" + eventId, {
          responseType: 'text' as 'json'
        }).subscribe(
          result => {
            observer.next(true);
          }, error => {
            this._dbService.setPriority(true);
            observer.next(false);
          }
        ).add(() => this._dbService.finishRound(roundNumber, eventId));
      })
    else {
      return new Observable<boolean>(observer => {
        this._dbService.finishRound(roundNumber, eventId).pipe(take(1)).subscribe(
          result => {
            observer.next(false);
          }
        );
      })
    }
  }

  syncRound(roundNumber: number, eventId: number): Observable<boolean> {
    if (!this._dbService.hasPriority())
      return new Observable(observer => {
        this._http.post<any>(this._baseUrl + "rounds/vault-update/" + roundNumber + "?eventId=" + eventId, null, {
          responseType: 'text' as 'json'
        }).subscribe(
          result => observer.next(true),
          error => {
            observer.next(false)
            this._dbService.setPriority(true);
          }
        );
      })
    else return of(false);
  }

  cancelRound(roundNumber: number, eventId: number): Observable<boolean> {
    if (!this._dbService.hasPriority())
      return new Observable(observer => {
        this._http.put<any>(this._baseUrl + "rounds/cancel/" + roundNumber + "?eventId=" + eventId, {
          responseType: 'text' as 'json'
        }).subscribe(
          result => observer.next(true),
          error => {
            this._dbService.setPriority(true);
            observer.next(false)
          }
        ).add(() => {
          this._dbService.cancelRound(roundNumber, eventId);
        });
      });
    else {
      this._dbService.cancelRound(roundNumber, eventId);
      return of(false);
    }
  }

  reactivateRound(roundNumber: number, eventId: number): Observable<boolean> {
    if (!this._dbService.hasPriority())
      return new Observable(observer => {
        this._http.put<any>(this._baseUrl + "rounds/uncancel/" + roundNumber + "?eventId=" + eventId, {
          responseType: 'text' as 'json'
        }).subscribe(
          result => {
            observer.next(true);
          },
          error => {
            this._dbService.setPriority(true);
            observer.next(false);
          }
        ).add(() => this._dbService.uncancelRound(roundNumber, eventId));
      });
    else {
      this._dbService.uncancelRound(roundNumber, eventId);
      return of(false);
    }
  }

  // getRound(roundNumber:number):Observable<any>{
  //   return this._http.get(this._baseUrl + "rounds/"+roundNumber);
  // }

  getRounds(eventID: number): Observable<Round[]> {
    if (!this._dbService.hasPriority())
      return new Observable(observer => {
        this._http.get<Round[]>(this._baseUrl + "rounds/with-flights?eventId=" + eventID).subscribe(
          rounds => {
            this._dbService.rewriteDatabase(rounds.slice()).pipe(take(1)).subscribe(result => {
              observer.next(rounds);
            });
          },
          error => {
            this._dbService.setPriority(true);
            this._dbService.readAllRounds(eventID).pipe(take(1)).subscribe(rounds => {
              observer.next(rounds);
            });
          }
        )
      });
    else {
      return new Observable(observer => {
        this._dbService.readAllRounds(eventID).pipe(take(1)).subscribe(rounds => {
          observer.next(rounds);
        });
      })
    }
  }
}

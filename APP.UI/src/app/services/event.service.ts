import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Event } from '../models/event';
import { IndexedDbService } from './indexed-db.service';
import { Round } from '../models/round';
import { take } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl, private _dbService: IndexedDbService) { }

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

  deleteEvent(eventId: number): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this._http.delete(this._baseUrl + "events/finish/" + eventId).subscribe(
        result => {
          this.eventId = null;
          observer.next(true);
        },
        error => observer.error(false))
    });
  }

  getEventId() {
    return this.eventId;
  }

  updateGeneralScore(eventId: number): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this._http.put(this._baseUrl + "events/total-score/" + eventId, null).subscribe(
        result => {
          if (this._dbService.hasPriority()) {
            this.updateAllRoundsFromDB(eventId).pipe(take(1)).subscribe(
              result => {
                if (result) {
                  this._dbService.setPriority(false);
                  this.updateGeneralScore(eventId).pipe(take(1)).subscribe(result => {
                    observer.next(result);
                  })
                } else {
                  this._dbService.setPriority(true);
                  observer.next(false);
                }
              }
            )
          } else {
            observer.next(true);
          }
        },
        error => {
          this._dbService.setPriority(true);
          observer.next(false);
        }
      )
    })
  }

  updateAllRoundsFromDB(eventId: number): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this._dbService.readAllRounds(eventId).pipe(take(1)).subscribe(result => {
        this._http.put(this._baseUrl + "rounds/synchronize-localdb/", result).subscribe(
          result => {
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
          },
          error => {
            this._dbService.setPriority(true);
            observer.next(false);
          }
        )
      })
    });
  }

  synchronizeWithVault(eventId: number): Observable<boolean> {
    return new Observable<boolean>(observer => {
      if (!this._dbService.hasPriority())
        this._http.put(this._baseUrl + "rounds/synchronize-vault?eventId=" + eventId, null).subscribe(
          result => {
            observer.next(true);
          },
          error => {
            this._dbService.setPriority(true);
            observer.next(false)
          }
        )
      else
        observer.next(false);
    })
  }
}

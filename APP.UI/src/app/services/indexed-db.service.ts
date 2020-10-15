import { Injectable } from '@angular/core';
import Dexie from 'dexie';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { Flight } from '../models/flight'
import { Round } from '../models/round';

@Injectable({
  providedIn: 'root'
})
export class IndexedDbService {

  private dbName = 'AviationModelling';
  private db: MyDatabase;
  private priority: boolean;

  constructor() {
    this.priority = false;
    this.db = new MyDatabase(this.dbName);

    this.db.open().catch(err => {
      console.error(`Open failed: ${err.stack}`);
    });
  }
  //--- GENERAL ---//

  rewriteDatabase(rounds: Round[]): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this.db.rounds.clear().then(() => {
        this.db.flights.clear().then(() => {
          rounds.forEach(round => {
            this.createRound(round).pipe(take(1)).subscribe(result => {
              round.flights.forEach(flight => {
                this.createFlight(flight, false);
              });
            });
          });
          observer.next(true);
        });
      });
    })
  }

  //--- FLIGHTS ---//

  createFlight(flight: Flight, updateRound = true) {
    this.db.flights.put(flight, { pilotId: flight.pilotId, roundNum: flight.roundNum, eventId: flight.eventId }).then(
      () => {
        if (updateRound) {
          this.readFlightsFromRound(flight.roundNum, flight.eventId).pipe(take(1)).subscribe(flights => {
            this.readRound(flight.roundNum, flight.eventId).pipe(take(1)).subscribe(round => {
              round.flights = flights;
              this.createRound(round);
            })
          })
        }
      }
    );
  }

  readBestFlight(roundNum: number, eventId: number): Observable<Flight> {
    return new Observable(observer => {
      this.db.flights
        .where(["pilotId+roundNum+eventId"])
        .between([Dexie.minKey, roundNum, eventId], [Dexie.maxKey, roundNum, eventId])
        .filter(f => f.roundNum == roundNum)
        .filter(x => x.seconds > 0)
        .sortBy("seconds").then(result => {
          observer.next(result[0]);
        });
    })
  }

  readFlightsFromRound(roundNum: number, eventId: number): Observable<Flight[]> {
    return new Observable(observable => {
      this.db.flights
        .where(["pilotId+roundNum+eventId"])
        .between([Dexie.minKey, roundNum, eventId], [Dexie.maxKey, roundNum, eventId])
        .filter(f => f.roundNum == roundNum)
        .toArray().then(result => {
          observable.next(result);
        });
    })
  }

  deleteFlight(flight: Flight) {
    this.db.flights.delete([flight.pilotId, flight.roundNum, flight.eventId])
  }

  //--- ROUNDS ---//

  createRound(round: Round): Observable<boolean> {
    return new Observable(observer => {
      this.db.rounds.put(round, { roundNum: round.roundNum, eventId: round.eventId }).then(() => {
        observer.next(true);
      })
    })
  }

  readRound(roundNum: number, eventId: number): Observable<Round> {
    return new Observable(observer => {
      this.db.rounds.where(["roundNum+eventId"]).equals([roundNum, eventId]).toArray().then(round => {
        observer.next(round[0]);
      });
    })
  }

  readAllRounds(eventId: number): Observable<Round[]> {
    return new Observable(observer => {
      this.db.rounds.filter(r => r.eventId == eventId).toArray().then(rounds => {
        observer.next(rounds);
      });
    });
  }

  countRoundScore(roundNum: number, eventId: number) {

  }

  countAllRoundsScore(eventId: number) {

  }

  finishRound(roundNum: number, eventId: number) : Observable<boolean> {
    return new Observable(observer => {
      this.readRound(roundNum, eventId).subscribe(result => {
        result.finished = true;
        this.createRound(result).pipe(take(1)).subscribe(result=>{
          observer.next(true);
        })
      });
    });
  }

  cancelRound(roundNum: number, eventId: number) {
    this.readRound(roundNum, eventId).subscribe(result => {
      result.cancelled = true;
      this.createRound(result).subscribe().unsubscribe();;
    });
  }

  uncancelRound(roundNum: number, eventId: number) {
    this.readRound(roundNum, eventId).subscribe(result => {
      result.cancelled = false;
      this.createRound(result).subscribe().unsubscribe();;
    });
  }

  deleteRound(roundNum: number, eventId: number) {
    this.db.rounds.delete([roundNum, eventId]);
    this.db.flights
      .where(["pilotId+roundNum+eventId"])
      .between([Dexie.minKey, roundNum, eventId], [Dexie.maxKey, roundNum, eventId])
      .delete();
  }

  //--- EVENT ---//

  updateGeneralScore(eventId: number) {

  }

  //--- MISC ---///

  hasPriority(): boolean {
    return this.priority;
  }

  setPriority(bool: boolean) {
    this.priority = bool;
  }
}

class MyDatabase extends Dexie {
  flights: Dexie.Table<Flight>;
  rounds: Dexie.Table<Round>;

  constructor(databaseName) {
    super(databaseName);
    this.version(1).stores({
      flights: '[pilotId+roundNum+eventId], flight',
      rounds: '[roundNum+eventId], round'
    });
    this.flights = this.table('flights'); // Just informing Typescript what Dexie has already done...
    this.rounds = this.table('rounds');
  }
}
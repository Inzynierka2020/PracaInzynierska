import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Round } from '../models/round';

@Injectable({
  providedIn: 'root'
})
export class RoundsService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }

  updateRound(roundNumber: number, eventId: number): Observable<any> {
    return this._http.put<any>(this._baseUrl + "rounds/update/" + roundNumber + "?eventId=" + eventId, {
      responseType: 'text' as 'json'
    });
  }

  updateAllRounds(eventId: number): Observable<any> {
    return this._http.put<any>(this._baseUrl + "rounds/update?eventId=" + eventId, {
      responseType: 'text' as 'json'
    });
  }
  startNewRound(roundNumber: number, numberOfGroups: number, eventId: number): Observable<Round> {
    let round: Round = {
      cancelled: false,
      eventId: eventId,
      finished: false,
      roundNum: roundNumber,
      flights: null,
      numberOfGroups: numberOfGroups,
      synchronized: false
    }
    return this._http.post<Round>(this._baseUrl + "rounds/new?eventId=" + eventId+"&numberOfGroups="+round.numberOfGroups+"&roundNum=" + round.roundNum,
     null, {
      responseType: 'text' as 'json'
    });
  }

  finishRound(roundNumber: number, eventId: number): Observable<any> {
    return this._http.put<any>(this._baseUrl + "rounds/finish/" + roundNumber + "?eventId=" + eventId, {
      responseType: 'text' as 'json'
    });
  }

  syncRound(roundNumber: number, eventId: number): Observable<any> {
    console.log(roundNumber, eventId);
    return this._http.post<any>(this._baseUrl + "rounds/vault-update/" + roundNumber + "?eventId=" + eventId, null, {
      responseType: 'text' as 'json'
    });
  }

  cancelRound(roundNumber: number, eventId: number): Observable<any> {
    return this._http.put<any>(this._baseUrl + "rounds/cancel/" + roundNumber + "?eventId=" + eventId, {
      responseType: 'text' as 'json'
    });
  }

  reactivateRound(roundNumber: number, eventId: number): Observable<any> {
    return this._http.put<any>(this._baseUrl + "rounds/uncancel/" + roundNumber + "?eventId=" + eventId, {
      responseType: 'text' as 'json'
    });
  }

  // getRound(roundNumber:number):Observable<any>{
  //   return this._http.get(this._baseUrl + "rounds/"+roundNumber);
  // }

  getRounds(eventID: number): Observable<Round[]> {
    return this._http.get<Round[]>(this._baseUrl + "rounds/with-flights?eventId=" + eventID);
  }
}

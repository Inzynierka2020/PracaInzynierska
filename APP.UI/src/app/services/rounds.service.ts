import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Round } from '../models/round';

@Injectable({
  providedIn: 'root'
})
export class RoundsService {
  
  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }
  
  updateRound(roundNumber: number):Observable<any>{
    return this._http.put<any>(this._baseUrl + "rounds/update/"+roundNumber,  {
      responseType: 'text' as 'json'
    });
  }

  updateAllRounds():Observable<any>{
    return this._http.put<any>(this._baseUrl + "rounds/update/",  {
      responseType: 'text' as 'json'
    });
  }

  startNewRound(roundNumber:number, eventId:number):Observable<Round>{
    console.log(roundNumber, eventId)
    return this._http.post<Round>(this._baseUrl + "rounds/new/"+eventId+"/"+roundNumber,  {
      responseType: 'text' as 'json'
    });
  }

  finishRound(roundNumber:number):Observable<any>{
    return this._http.put<any>(this._baseUrl + "rounds/finish/"+roundNumber, {
      responseType: 'text' as 'json'
    });
  }

  cancelRound(roundNumber:number):Observable<any>{
    return this._http.put<any>(this._baseUrl + "rounds/cancel/"+roundNumber, {
      responseType: 'text' as 'json'
    });
  }

  uncancelRound(roundNumber:number):Observable<any>{
    return this._http.put<any>(this._baseUrl + "rounds/uncancel/"+roundNumber, {
      responseType: 'text' as 'json'
    });
  }

  getRound(roundNumber:number):Observable<any>{
    return this._http.get(this._baseUrl + "rounds/"+roundNumber);
  }

  getRounds():Observable<Round[]>{
    return this._http.get<Round[]>(this._baseUrl + "rounds/list/with-flights");
  }
}

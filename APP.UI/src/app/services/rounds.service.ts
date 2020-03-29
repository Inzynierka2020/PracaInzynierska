import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoundsService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }

  startNewRound(roundNumber:number):Observable<any>{
    return this._http.post<any>(this._baseUrl + "rounds/create/"+roundNumber, {
      responseType: 'text' as 'json'
    });
  }

  finishRound(roundNumber:number):Observable<any>{
    return this._http.put<any>(this._baseUrl + "rounds/finish/"+roundNumber, {
      responseType: 'text' as 'json'
    });
  }

  getRound(roundNumber:number):Observable<any>{
    return this._http.get(this._baseUrl + "rounds/"+roundNumber);
  }
}

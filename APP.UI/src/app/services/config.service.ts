import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Settings } from '../models/settings';

interface UserConfig{
  vaultLogin: string,
  vaultPassword: string,
  vaultUrl: string
}

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor(private _http: HttpClient, @Inject('BASE_URL') private _baseUrl) { }

  updateConfig(settings: Settings): Observable<any> {
    var config = {
      vaultLogin: settings.login,
      vaultPassword: settings.password,
      vaultUrl: settings.apiUrl
    }
    return this._http.put(this._baseUrl + "users/update", config, {
      responseType: 'text' as 'json'
    });
  }

  getConfig(): Observable<UserConfig> {
    return this._http.get<UserConfig>(this._baseUrl + "users/vault-config");
  }
}

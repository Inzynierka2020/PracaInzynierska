import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Settings } from '../models/settings';
import { ThemeService } from '../services/theme.service';
import { EventService } from '../services/event.service';
import { TranslateService } from '@ngx-translate/core';
import { ClockService } from '../services/clock.service';
import { ConfigService } from '../services/config.service';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<SettingsComponent>, 
    public themeService: ThemeService, 
    public _eventService: EventService, 
    public _clockService: ClockService, 
    private _configService: ConfigService,
    public translate: TranslateService,
    private _authService: AuthService) {
    if (this._eventService.getEventId())
      this.noEvent = false
    else
      this.noEvent = true;
  }

  languages = ['en', 'pl'];

  settings: Settings = {
    apiUrl: "http://www.f3xvault.com/api.php?",
    login: "piotrek.adamczykk@gmail.com",
    password: "ascroft",
    eventId: 1834
  }

  noEvent = true;
  language: 'en';

  ngOnInit() {

  }

  connect(){
    this._clockService.connectDevice();
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    this._configService.updateConfig(this.settings).subscribe(result => {
      this.close();
    })
  }

  finishEvent() {
    let eventId = localStorage.getItem('eventId');
    localStorage.removeItem('eventId');
    this._eventService.deleteEvent(Number(eventId)).subscribe(result => {
      window.location.reload();
    }, error => {
      window.location.reload();
      console.log("INFO: Deleting event failed. There is no such event in the database !");
    });
  }

  toggleTheme() {
    if (this.themeService.isSecondTheme()) {
      this.themeService.setFirstTheme();
    } else {
      this.themeService.setSecondTheme();
    }
  }

  toggleLang(lang: string) {
    this.translate.use(lang);
    localStorage.setItem("lang", lang);
  }

  signOut(){
    this._authService.logout();
    this.dialogRef.close();
  }
}

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Settings } from '../models/settings';
import { ThemeService } from '../services/theme.service';
import { EventService } from '../services/event.service';
import { TranslateService } from '@ngx-translate/core';
import { ClockService } from '../services/clock.service';
import { ConfigService } from '../services/config.service';
import { AuthService } from '../auth/auth.service';
import { SnackService } from '../services/snack.service';

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
    private _authService: AuthService,
    private _snackService: SnackService) {
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

  progressing = false;

  noEvent = true;
  noLogout = false;
  language: 'en';

  ngOnInit() {

  }

  connect() {
    this._clockService.connectDevice();
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    this.themeService.setThemeForStorage();
    this._configService.updateConfig(this.settings).subscribe(result => {
      this.close();
    })
  }

  finishEvent() {
    this.progressing = true;
    let eventId = localStorage.getItem('eventId');
    this._eventService.deleteEvent(Number(eventId)).subscribe(result => {
      localStorage.removeItem('eventId');
      window.location.reload();
    }, error => {
      this._snackService.open("NO SERVER CONNECTION. CANNOT FINISH EVENT.")
      this.noEvent = true;
    }).add(() => this.progressing = false);
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

  signOut() {
    this.progressing = true;
    var sub = this._authService.logout().subscribe(result => {
      if (result) {
        this.dialogRef.close();
      } else {
        this._snackService.open("NO SERVER CONNECTION. CANNOT SIGN OUT.")
        this.noLogout = true;
      }
      sub.unsubscribe();
    }).add(() => {
      this.progressing = false;
    });
  }
}

import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Settings } from '../models/settings';
import { ThemeService } from '../services/theme.service';
import { EventService } from '../services/event.service';
import { TranslateService } from '@ngx-translate/core';
import { ClockService } from '../services/clock.service';
import { ConfigService } from '../services/config.service';
import { AuthService } from '../auth/auth.service';
import { SnackService } from '../services/snack.service';
import { PwaService } from '../services/pwa.service';
import { BestFlightType, EventRules, RulesService } from '../services/rules.service';

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
    private _snackService: SnackService,
    private _pwaService: PwaService,
    private _rulesService: RulesService) {
    if (this._eventService.getEventId())
      this.noEvent = false
    else
      this.noEvent = true;

    this.keys = Object.keys(this.types).filter(f => !isNaN(Number(f)))
      .map(k => parseInt(k));
  }

  languages = ['en', 'pl'];

  keys: any[]
  types = BestFlightType;

  settings: Settings = {
    apiUrl: "http://www.f3xvault.com/api.php?",
    login: "",
    password: "",
    eventId: null
  }

  rules: EventRules = {
    pilotInGroupCount: 10,
    bestFlightType: BestFlightType.Round
  }

  progressing = false;

  noEvent = true;
  noLogout = false;
  language: 'en';

  ngOnInit() {
    this.settings.eventId = this._eventService.getEventId();
    this.getRules();
  }

  getRules() {
    var rules = this._rulesService.getRules();
    if (rules)
      this.rules = rules;
  }

  setRules() {
    this.rules.pilotInGroupCount = parseInt(this.rules.pilotInGroupCount.toString());
    this._rulesService.setRules(this.rules);
  }

  connect() {
    this._clockService.connectDevice();
  }

  close() {
    console.log(this.rules)
    console.log(this.rules.bestFlightType === BestFlightType.Event);
    this.dialogRef.close();
  }

  save() {
    this.themeService.setThemeForStorage();
    this.setRules();
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
      this._snackService.open('NoConnection.Finish')
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
        this._snackService.open('NoConnection.Logout')
        this.noLogout = true;
      }
      sub.unsubscribe();
    }).add(() => {
      this.progressing = false;
    });
  }

  requestNewVersion() {
    this._pwaService.requestNewVersion();
  }

  addToScreen() {
    this._pwaService.addToHomeScreen();
  }
}

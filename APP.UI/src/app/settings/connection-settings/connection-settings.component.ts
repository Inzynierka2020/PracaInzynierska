import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation, OnChanges, SimpleChanges } from '@angular/core';
import { Settings } from '../../models/settings';
import { ConfigService } from '../../services/config.service';

@Component({
  selector: 'app-connection-settings',
  templateUrl: './connection-settings.component.html',
  styleUrls: ['./connection-settings.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ConnectionSettingsComponent implements OnInit {

  @Input()
  settings: Settings;
  @Output()
  settingsChange = new EventEmitter<any>();

  constructor(private _configService: ConfigService) { }

  disabled = false;

  ngOnInit() {
    let eventId = localStorage.getItem('eventId');
    if (eventId) {
      this.disabled = true;
    } else {
      this.disabled = false;
    }
    this._configService.getConfig().subscribe(userConfig => {
      if (userConfig.vaultUrl)
        this.settings.apiUrl = userConfig.vaultUrl;
      if (userConfig.vaultLogin)
        this.settings.login = userConfig.vaultLogin;
      if (userConfig.vaultPassword)
        this.settings.password = userConfig.vaultPassword;

      this.settingsChange.emit(this.settings);
    })
  }

  onChange(event) {
    this.settingsChange.emit(this.settings);
  }
}

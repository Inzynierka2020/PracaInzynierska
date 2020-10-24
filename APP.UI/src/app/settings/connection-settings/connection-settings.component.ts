import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation } from '@angular/core';
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
      this.settings.apiUrl = userConfig.vaultUrl;
      this.settings.login = userConfig.vaultLogin;
      this.settings.password = userConfig.vaultPassword;
    })
  }
  ngOnChange() {
    this.settingsChange.emit(this.settings);
  }


}

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Settings } from '../models/settings';
import { ThemeService } from '../services/theme.service';
import { EventService } from '../services/event.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<SettingsComponent>, public themeService: ThemeService, public _eventService: EventService) {
    if (this._eventService.getEventId())
      this.noEvent = false
    else
      this.noEvent = true;
  }

  settings: Settings = {
    apiUrl: "http://www.f3xvault.com/api.php?",
    login: "piotrek.adamczykk@gmail.com",
    password: "ascroft",
    eventId: 1834
  }

  noEvent = true;

  ngOnInit() {

  }

  close() {
    this.dialogRef.close();
  }

  save() {

  }

  finishEvent() {
    let eventId = localStorage.getItem('eventId');
    localStorage.removeItem('eventId');
    this._eventService.deleteEvent(Number(eventId)).subscribe(result => {
      window.location.reload();
    }, error => {
      window.location.reload();
      console.log("Deleting event failed. There is no such event in the database !");
    });
  }

  toggleTheme() {
    if (this.themeService.isSecondTheme()) {
      this.themeService.setFirstTheme();
    } else {
      this.themeService.setSecondTheme();
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Settings } from '../models/settings';
import { ThemeService } from '../services/theme.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<SettingsComponent>, public themeService: ThemeService) {
  }

  settings: Settings= {
    apiUrl: "http://www.f3xvault.com/api.php?",
    login: "piotrek.adamczykk@gmail.com",
    password: "ascroft",
    eventId: 1834
  }
  
  ngOnInit() {
  }

  close() {
    this.dialogRef.close();
  }

  save() {

  }

  toggleTheme() {
    console.log("DUPA")
    if (this.themeService.isSecondTheme()) {
      this.themeService.setFirstTheme();
    } else {
      this.themeService.setSecondTheme();
    }
  }
}

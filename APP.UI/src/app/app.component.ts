import { Component } from '@angular/core';
import { Event } from '../app/models/event'
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(public translate: TranslateService) {
    translate.addLangs(['en', 'pl']);
    translate.setDefaultLang('pl');
  }

  event: Event;
  eventStarted = false;

  start() {
    if (event)
      this.eventStarted = true;
  }
}
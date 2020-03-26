import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { ToolbarComponent } from './toolbar/toolbar.component';
import { AppComponent } from './app.component';
import { MaterialModule } from 'src/modules/material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { TabComponent } from './tab/tab.component';
import { ScoreComponent } from './score/score.component';
import { RoundComponent } from './round/round.component';
import { BrowseComponent } from './browse/browse.component';
import { SettingsComponent } from './settings/settings.component';
import { NewRoundDialogComponent } from './new-round-dialog/new-round-dialog.component';
import { FormsModule } from '@angular/forms';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { PlayerComponent } from './player/player.component';
import { WindComponent } from './wind/wind.component';
import { HttpClientModule } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { EventComponent } from './event/event.component';

const appRoutes: Routes = [
  { path: '', component: EventComponent },
  { path: 'tab', component: TabComponent },
  { path: 'score', component: ScoreComponent },
  // { path: '**', component: PageNotFoundComponent }
];

export function getBaseUrl() {
  var url = document.getElementsByTagName('base')[0].href.replace(/:\d{1,}\/|\/$/, '');
  url += ':'+environment.apiPort+'/';
  console.log(url);
  return url; 
}

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    TabComponent,
    ScoreComponent,
    RoundComponent,
    BrowseComponent,
    SettingsComponent,
    NewRoundDialogComponent,
    ConfirmDialogComponent,
    PlayerComponent,
    WindComponent,
    EventComponent,
  ],
  imports: [
    BrowserModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [ { provide: 'BASE_URL', useFactory: getBaseUrl, deps: [] }],
  bootstrap: [AppComponent],
  entryComponents: [SettingsComponent, NewRoundDialogComponent, ConfirmDialogComponent, PlayerComponent]
})
export class AppModule { }

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { ToolbarComponent } from './toolbar/toolbar.component';
import { AppComponent } from './app.component';
import { MaterialModule } from 'src/modules/material/material.module';
import { TabComponent } from './tab/tab.component';
import { ScoreComponent } from './score/score.component';
import { RoundComponent } from './round/round.component';
import { BrowseComponent} from './browse/browse.component';
import { SettingsComponent } from './settings/settings.component';
import { NewRoundDialogComponent } from './new-round-dialog/new-round-dialog.component';
import { FormsModule } from '@angular/forms';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { PlayerComponent } from './player/player.component';
import { WindComponent } from './wind/wind.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { EventComponent } from './event/event.component';
import { GroupPipe } from './pipes/group.pipe';
import { WarningSnackComponent } from './wind/warning-snack/warning-snack.component';
import { ConnectionSettingsComponent } from './settings/connection-settings/connection-settings.component';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

export function getBaseUrl() {
  var url = document.getElementsByTagName('base')[0].href.replace(/:\d{1,}\/|\/$/, '');
  url += ':'+environment.apiPort+'/';
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
    GroupPipe,
    WarningSnackComponent,
    ConnectionSettingsComponent,
  ],
  imports: [
    BrowserModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: httpTranslateLoader,
        deps: [HttpClient]
      }
    })
  ],
  providers: [ { provide: 'BASE_URL', useFactory: getBaseUrl, deps: [] }],
  bootstrap: [AppComponent],
  entryComponents: [SettingsComponent, NewRoundDialogComponent, ConfirmDialogComponent, PlayerComponent, WarningSnackComponent]
})
export class AppModule { }

export function httpTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
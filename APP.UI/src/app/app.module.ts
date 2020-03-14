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

const appRoutes: Routes = [
  { path: '', component: TabComponent },
  { path: 'score', component: ScoreComponent },
  // { path: '**', component: PageNotFoundComponent }
];

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
    PlayerComponent
  ],
  imports: [
    BrowserModule,
    MaterialModule,
    FormsModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [SettingsComponent, NewRoundDialogComponent, ConfirmDialogComponent, PlayerComponent]
})
export class AppModule { }

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

const appRoutes: Routes = [
  { path: '', component:  TabComponent},
  { path: 'score', component:  ScoreComponent},
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
    SettingsComponent
  ],
  imports: [
    BrowserModule,
    MaterialModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [SettingsComponent]
})
export class AppModule { }

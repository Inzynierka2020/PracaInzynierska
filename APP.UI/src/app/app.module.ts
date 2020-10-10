import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { ToolbarComponent } from "./toolbar/toolbar.component";
import { AppComponent } from "./app.component";
import { MaterialModule } from "../modules/material/material.module";
import { TabComponent } from "./tab/tab.component";
import { ScoreComponent } from "./score/score.component";
import { RoundComponent } from "./round/round.component";
import { BrowseComponent } from "./browse/browse.component";
import { SettingsComponent } from "./settings/settings.component";
import { NewRoundDialogComponent } from "./new-round-dialog/new-round-dialog.component";
import { FormsModule } from "@angular/forms";
import { ConfirmDialogComponent } from "./confirm-dialog/confirm-dialog.component";
import { PlayerComponent } from "./player/player.component";
import { WindComponent } from "./wind/wind.component";
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { EventComponent } from "./event/event.component";
import { GroupPipe } from "./pipes/group.pipe";
import { WarningSnackComponent } from "./wind/warning-snack/warning-snack.component";
import { ConnectionSettingsComponent } from "./settings/connection-settings/connection-settings.component";

import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { AuthComponent } from "./auth/auth.component";
import { AuthInterceptorService } from './auth/auth-interceptor.service';
import { WrapperComponent } from './wrapper/wrapper.component';
import { AppRoutingModule } from './app-routing.module';
import { environment } from "../environments/environment";
import { ServiceWorkerModule } from '@angular/service-worker';

export function getBaseUrl() {
  var url = document
    .getElementsByTagName("base")[0]
    .href.replace(/:\d{1,}\/|\/$/, "");
  url += environment.apiUrl;
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
    AuthComponent,
    WrapperComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: httpTranslateLoader,
        deps: [HttpClient],
      },
    }),
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),
  ],
  providers: [
    { provide: "BASE_URL", useFactory: getBaseUrl, deps: [] },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    SettingsComponent,
    NewRoundDialogComponent,
    ConfirmDialogComponent,
    PlayerComponent,
    WarningSnackComponent,
  ],
})
export class AppModule {}

export function httpTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

import { Component, HostListener, OnInit } from "@angular/core";
import { Event } from "../app/models/event";
import { TranslateService } from "@ngx-translate/core";
import { AuthService } from "./auth/auth.service";
import { PwaService } from "./services/pwa.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit {
  constructor(private authService: AuthService, private _pwaService: PwaService) {}

  @HostListener('window:beforeinstallprompt', ['$event'])
  onbeforeinstallprompt(e) {
    // Prevent Chrome 67 and earlier from automatically showing the prompt
    e.preventDefault();
    // Stash the event so it can be triggered later.
    this._pwaService.deferredPrompt = e;
    this._pwaService.showButton = true;
  }
  @HostListener('window:appinstalled', ['$event'])
  appinstalled(e) {
    this._pwaService.showButton = false;
  }

  ngOnInit(): void {
    this.authService.autoLogin();
  }
}

import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { AuthService } from "../auth/auth.service";
import { ThemeService } from "../services/theme.service";

@Component({
  selector: "app-wrapper",
  templateUrl: "./wrapper.component.html",
  styleUrls: ["./wrapper.component.css"],
})
export class WrapperComponent {
  constructor(
    public translate: TranslateService,
    public authService: AuthService,
    private _themeService: ThemeService
  ) {
    translate.addLangs(["en", "pl"]);
    translate.setDefaultLang("pl");
    var lang = localStorage.getItem("lang");
    if (lang)
      this.translate.use(lang);
    else {
      localStorage.setItem("lang", "en");
      this.translate.use("en");
    }
    _themeService.setThemeFromStorage();
  }

  event: Event;
  eventStarted = false;

  start() {
    if (event) this.eventStarted = true;
  }
}

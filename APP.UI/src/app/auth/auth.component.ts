import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SnackService } from '../services/snack.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  isLoginMode = true;
  isAuthenticated = false;

  constructor(private authService: AuthService,
    private router: Router,
    private _translateService: TranslateService,
    private _snackService: SnackService) { }

  ngOnInit() {
    var lang = localStorage.getItem("lang");
    if (lang)
      this._translateService.use(lang);
    else {
      localStorage.setItem("lang", "en");
      this._translateService.use("en");
    }
  }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    const username = form.value.username;
    const password = form.value.password;
    const email = form.value.email;

    if (this.isLoginMode) {
      this.authService.login(username, password).subscribe(userData => {
        console.log("INFO: " + userData);
        this.router.navigate(['../'])
      }, error => {
          this._snackService.open("CONNECTION LOST. YOU CAN'T LOG IN AT THE MOMENT");
      });
    } else {
      this.authService.register(username, password, email).subscribe(token => {
        this.isLoginMode=true;
        console.log("INFO" + token)
      }, error => {
        if(error.status == 0){
          this._snackService.open("CONNECTION LOST. YOU CAN'T REGISTER APPLICATION AT THE MOMENT");
        }
      });
    }

    form.reset();
  }
}

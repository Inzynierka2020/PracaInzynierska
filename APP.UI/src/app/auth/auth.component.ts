import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  isLoginMode = false;
  isAuthenticated = false;

  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
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
        console.log(userData);
        this.router.navigate(['../'])
      });
    } else {

      this.authService.register(username, password, email).subscribe(token => console.log(token));
    }

    form.reset();
  }

}

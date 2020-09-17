import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { tap } from "rxjs/operators";
import { Token } from "./token.model";
import { BehaviorSubject } from "rxjs";
import { Router } from '@angular/router';

export interface UserResponse {
  id: number;
  username: string;
  email: string;
  password: string;
}

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient,
    private router: Router) {}

  token = new BehaviorSubject<Token>(null);
  private tokenExpirationTimer: any;

  register(username: string, password: string, email: string) {
    return this.http
      .post<UserResponse>("http://localhost:8080/register", {
        username,
        password,
        email,
      })
      .pipe(
        tap((user) => {
          console.log(user.username + "registered!");
        })
      );
  }

  login(username: string, password: string) {
    return this.http
      .post<{ expirationDate: string; jwttoken: string }>(
        "http://localhost:8080/authenticate",
        { username: username, password: password }
      )
      .pipe(
        tap((response) => {
          this.handleAuthentication(response.jwttoken, response.expirationDate);
        })
      );
  }

  private handleAuthentication(token: string, expDate: string) {
    // aktualna data (w ms) + expiresIn * 1000 (aby tez w ms)
    const expirationDate = new Date(expDate);
    let tokenId = "Bearer " + token;
    const tokenData = new Token(tokenId, expirationDate);
    this.token.next(tokenData);
    const expirationTime = expirationDate.getTime() - new Date().getTime();
    this.autoLogout(expirationTime);
    localStorage.setItem("tokenData", JSON.stringify(tokenData));
  }

  autoLogout(expirationDuration: number) {
    // console.log(`Token expires in: ${expirationDuration/1000} s`);
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }

  logout() {
    this.token.next(null);
    localStorage.removeItem("tokenData");
    this.router.navigate(["/auth"]);
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogin() {
    const tokenData: {
      _token: string;
      _tokenExpirationDate: string;
    } = JSON.parse(localStorage.getItem("tokenData"));
    if(!tokenData) {
      return;
    }
    const actualToken = new Token(
      tokenData._token,
      new Date(tokenData._tokenExpirationDate)
    );
    if (actualToken.token) {
      this.token.next(actualToken);
      const expirationDuration = new Date(tokenData._tokenExpirationDate).getTime() - new Date().getTime();
      this.autoLogout(expirationDuration);
    }
  }


  isUserLoggedIn() {
    if (localStorage.getItem("tokenData")) {
      return true;
    }
    return false;
  }
}

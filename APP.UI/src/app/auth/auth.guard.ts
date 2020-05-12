import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
} from "@angular/router";
import { Injectable } from "@angular/core";
import { AuthService } from "./auth.service";
import { map, take } from "rxjs/operators";

@Injectable({ providedIn: "root" })
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | boolean
    | import("@angular/router").UrlTree
    | import("rxjs").Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {
    return this.authService.token.pipe(
      // pobierz wartosc tylko raz i od razu odsubscribuj
      take(1),
      map((token) => {
        // jesli istnieje user to isAuth = true
        const isAuth = !!token;
        if (isAuth) {
          return true;
        }
        return this.router.createUrlTree(['/auth']);
      })
    );
  }
}

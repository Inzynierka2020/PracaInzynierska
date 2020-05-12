import { Injectable } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpParams,
} from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthService } from "./auth.service";
import { take, exhaustMap } from "rxjs/operators";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<import("@angular/common/http").HttpEvent<any>> {

    // tylko dla BehaviorSubject, mozemy uzyc pipe(take(1)) dzieki czemu pobierze nam tylko raz i od razu odsubscribuje
    // exhaustMap sprawia, ze po pobraniu nam usera i zasubskrybowaniu go, przelacza automatycznie na drugi observable
    return this.authService.token.pipe(
      take(1),
      exhaustMap((token) => {

        if(!token) {
          return next.handle(req);
        }

        const modifiedReq = req.clone({

          setHeaders: {
            Authorization: token.token
          }
        })
        return next.handle(modifiedReq);
      })
    );
  }
}

import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { WarningSnackComponent } from '../wind/warning-snack/warning-snack.component';

@Injectable({
  providedIn: 'root'
})
export class SnackService {
  durationInSeconds: 5;

  constructor(private _snackBar: MatSnackBar) { }

  open(msg: String){
    this._snackBar.openFromComponent(WarningSnackComponent, {
      duration: 3000,
      data: msg
    });
  }
}

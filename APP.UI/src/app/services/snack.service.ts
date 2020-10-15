import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { WarningSnackComponent } from '../wind/warning-snack/warning-snack.component';

@Injectable({
  providedIn: 'root'
})
export class SnackService {
  durationInSeconds: 1;

  constructor(private _snackBar: MatSnackBar) { }

  open(msg: String){
    this._snackBar.openFromComponent(WarningSnackComponent, {
      duration: this.durationInSeconds * 100,
      data: msg
    });
  }
}

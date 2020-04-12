import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { viewAttached } from '@angular/core/src/render3/instructions';
import { MatSnackBarRef } from '@angular/material/snack-bar';

@Component({
  selector: 'app-warning-snack',
  templateUrl: './warning-snack.component.html',
  styleUrls: ['./warning-snack.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class WarningSnackComponent implements OnInit {

  constructor(private barref: MatSnackBarRef<WarningSnackComponent>) { }

  ngOnInit() {
  }

  dismiss(){
    this.barref.dismiss();
  }
}
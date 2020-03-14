import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-new-round-dialog',
  templateUrl: './new-round-dialog.component.html',
  styleUrls: ['./new-round-dialog.component.css']
})
export class NewRoundDialogComponent implements OnInit {

  groups=false;

  constructor(public dialogRef: MatDialogRef<NewRoundDialogComponent>) { }

  ngOnInit() {
  }

}
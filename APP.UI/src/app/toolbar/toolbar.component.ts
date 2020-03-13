import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SettingsComponent } from '../settings/settings.component';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  now: number;

  constructor(public dialog: MatDialog) {
    setInterval(() => {
      this.now = Date.now();
    }, 1);
  }

  ngOnInit() {
  }

  openSettings(){
    const dialogRef = this.dialog.open(SettingsComponent, {
      width: '90%',
      maxWidth: '1000px',
      height: '90%',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}

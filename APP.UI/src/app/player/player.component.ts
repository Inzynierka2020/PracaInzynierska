import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {

  constructor(public dialog: MatDialog, public dialogRef: MatDialogRef<PlayerComponent>) { }

  ngOnInit() {
  }

  savePlayer(){
    var confirmDialogRef = this.dialog.open(ConfirmDialogComponent,{
      width: '70%',
      maxWidth: '500px',
      disableClose: true
    });
    confirmDialogRef.afterClosed().subscribe(result=>{
      if(result){
        this.dialogRef.close("true")
      }
    })
  }

}

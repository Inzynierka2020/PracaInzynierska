import { Component, OnInit, Input } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {

  constructor(public dialog: MatDialog, public dialogRef: MatDialogRef<PlayerComponent>) { }

  @Input()
  editMode=true;

  ngOnInit() {
  }

  dNS = false;
  dNF = false;

  didNotFinish(){
    if(this.dNF){
      this.dNF= false;
    }else{
      this.dNF=true;
      this.dNS=false;
    }
  }

  didNotStart(){
    if(this.dNS){
      this.dNS= false;
    }else{
      this.dNS=true;
      this.dNF=false;
    }
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

  back(){
    this.dialogRef.close("true")
  }

  switchEditMode(){
    var confirmDialogRef = this.dialog.open(ConfirmDialogComponent,{
      width: '70%',
      maxWidth: '500px',
      disableClose: true
    });
    confirmDialogRef.afterClosed().subscribe(result=>{
      if(result){
        this.editMode=!this.editMode;
      }
    })
  }

}

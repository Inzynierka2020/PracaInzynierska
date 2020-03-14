import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-round',
  templateUrl: './round.component.html',
  styleUrls: ['./round.component.css']
})
export class RoundComponent implements OnInit {
  @Output()
  finished = new EventEmitter<boolean>();

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  finishRound(){
    var dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '70%',
      maxWidth: '500px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result=>{
      if(result){
        this.finished.emit(false);
      }
    });
  }
}

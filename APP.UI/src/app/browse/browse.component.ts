import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})
export class BrowseComponent implements OnInit {

  @Input()
  roundNumber = 0;

  editMode=false;
  group='A';

  constructor() { }

  ngOnInit() {
  }

  changeGroup(group){
    this.group=group;
  }

  changeMode(){
    this.editMode=!this.editMode;
  }
}

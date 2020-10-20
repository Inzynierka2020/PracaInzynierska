import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { MatSnackBarRef, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-warning-snack',
  templateUrl: './warning-snack.component.html',
  styleUrls: ['./warning-snack.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class WarningSnackComponent implements OnInit {

  public msg: string;

  constructor(private barref: MatSnackBarRef<WarningSnackComponent>, @Inject(MAT_SNACK_BAR_DATA) public data: string, private translate: TranslateService) {
    this.msg = this.translate.instant(data);
  }

  ngOnInit() {
  }

  dismiss() {
    this.barref.dismiss();
  }
}
import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit {
  @Input() message: string;
  @Input() messageType: string;
  @ViewChild('alert', { static: false }) alert: ElementRef;
  constructor() { }

  ngOnInit() {
  }

    closeAlert() { //closing the alert while press X 
    this.alert.nativeElement.classList.remove('show');
  }

}

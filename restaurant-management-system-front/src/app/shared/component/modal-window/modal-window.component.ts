import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-window',
  templateUrl: './modal-window.component.html',
  styleUrls: ['./modal-window.component.css']
})
export class ModalWindowComponent implements OnInit {
  windowHeader: string;
  message: string;
  inquiry = false;
  messageType: string;
  passEntry: EventEmitter<any> = new EventEmitter();


  constructor(private modalService: NgbModal, public activeModal: NgbActiveModal) {
  }

  ngOnInit() {
  }
  passBack(event) {
    this.passEntry.emit(event.target.value);
  }

}

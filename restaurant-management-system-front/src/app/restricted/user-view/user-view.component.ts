import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserviewContainerService } from './service/userview-container.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalWindowComponent } from 'src/app/shared/component/modal-window/modal-window.component';

@Component({
  selector: 'app-user-view',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements OnInit {
  tableId: number;
  constructor(private userviewContainer: UserviewContainerService, private modalService: NgbModal) { }

  ngOnInit() {
  }

  bookTable(event) {
    this.tableId = event.target.value;
    this.viewModal('Book Table', 'Are You Sure You Want To Book Table ' + this.tableId + '?', true);


  }

  viewModal(header: string, message: string, inquiry: boolean) {

    const modalRef = this.modalService.open(ModalWindowComponent);
    modalRef.componentInstance.windowHeader = header;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.inquiry = inquiry;

    modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
      if (receivedEntry === 'yes') {
        this.userviewContainer.bookTable(this.tableId);
        modalRef.close();
      } else {
        modalRef.close();
      }
    });

  }

}

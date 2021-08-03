import { Component, OnInit } from '@angular/core';
import { AdminViewContainerService } from './service/admin-view-container.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TableModalComponent } from '../table-modal/table-modal.component';
import { ModalWindowComponent } from 'src/app/shared/component/modal-window/modal-window.component';
import { AdminViewService } from './service/admin-view.service';

@Component({
  selector: 'app-admin-view',
  templateUrl: './admin-view.component.html',
  styleUrls: ['./admin-view.component.css']
})
export class AdminViewComponent implements OnInit {
  bookId: number;
  tableId: number;
  constructor(private adminContainerService: AdminViewContainerService, private modalService: NgbModal,
    private AdminViewServie: AdminViewService) { }

  ngOnInit() {
  }

  viewModalAddTable() {
    const modalRef = this.modalService.open(TableModalComponent);
  }
  cancelBooking(event) {
    this.bookId = event.target.value;
    this.viewModalDeleteBooking('Cancel Booking', 'Are you sure to canel this booking?', true);

  }
  deleteTable(event) {
    this.tableId = event.target.value;
    this.viewModalDeleteTable('Deleting Table ', 'Are you sure to Delete table?', true);


  }

  viewModalDeleteBooking(header: string, message: string, inquiry: boolean) {

    const modalRef = this.modalService.open(ModalWindowComponent);
    modalRef.componentInstance.windowHeader = header;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.inquiry = inquiry;

    modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
      if (receivedEntry === 'yes') {

        this.adminContainerService.cancelBooking(this.bookId);

        modalRef.close();
      } else {
        console.log('cancel deleting booking request')
        modalRef.close();
      }
    });

  }
  viewModalDeleteTable(header: string, message: string, inquiry: boolean) {

    const modalRef = this.modalService.open(ModalWindowComponent);
    modalRef.componentInstance.windowHeader = header;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.inquiry = inquiry;

    modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
      if (receivedEntry === 'yes') {

        this.adminContainerService.deleteTable(this.tableId);

        modalRef.close();
      } else {
        console.log('cancel deleting table ')
        modalRef.close();
      }
    });
  }

}

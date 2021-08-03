import { Injectable } from '@angular/core';
import { AdminViewService } from './admin-view.service';
import { IRestaurantTable } from '../../model/RestaurantTable';
import { NgbCalendar, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IFilter } from '../../user-view/service/userview-container.service';
import { IBookDto } from '../../model/BookDto';
import { ModalWindowComponent } from 'src/app/shared/component/modal-window/modal-window.component';

@Injectable({
  providedIn: 'root'
})
export class AdminViewContainerService {
  static dangerMessageType = 'danger';
  static successMessageType = 'success';
  IsSearchBookingRequest = false;
  pageIndex = 1;
  pageSize = 3;
  totalElements = 0;
  tables: IRestaurantTable[];
  hour = 12;
  bookDate: any;
  today: any;
  filter: IFilter = {} as any;
  bookings: IBookDto[];


  constructor(private adminService: AdminViewService, private calendar: NgbCalendar, private modalService: NgbModal) {
    this.bookDate = this.calendar.getToday();
    this.today = this.calendar.getToday();
  }

  getAllTables(page: number) {
    this.IsSearchBookingRequest = false;
    page = page - 1;
    this.adminService.getTables(page, this.pageSize).subscribe((res: any) => {
      this.tables = res.content;
      this.totalElements = res.totalElements;
    });

  }

  enableBookSearchDiv(event) {
    this.IsSearchBookingRequest = true;
    this.tables = null;
    this.totalElements = 0;
  }

  getBookedTable(page: number) {
    page = page - 1;
    this.constructFilter(page);
    this.adminService.getBookedTable(this.filter).subscribe((res: any) => {
      this.bookings = res.bookList;
      this.totalElements = res.totalElements;
    });

  }

  constructFilter(page: number) {
    this.filter.pageIndex = page;
    this.filter.dateFilter = new Date(this.bookDate.year, this.bookDate.month - 1, this.bookDate.day);
    this.filter.dateFilter.setHours(this.hour);
    this.filter.pageSize = this.pageSize;
  }


  viewModalResponse(message: string, messageType: string, inquiry: boolean) {

    console.log(' Result ....');

    const modalRef = this.modalService.open(ModalWindowComponent);

    modalRef.componentInstance.windowHeader = 'Result';
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.inquiry = inquiry;
    modalRef.componentInstance.messageType = messageType;
  }

  cancelBooking(bookingId: number) {
    this.adminService.cancelBooking(bookingId).subscribe((res: any) => {
      this.viewModalResponse(res.description, AdminViewContainerService.successMessageType, false);
      this.getBookedTable(this.pageIndex);

    }, error => {
      console.log(error);

      if (error.error.description) {
        this.viewModalResponse(error.error.description, AdminViewContainerService.dangerMessageType, false);
      } else {
        this.viewModalResponse(error.statusText, AdminViewContainerService.dangerMessageType, false);
      }
    }
    );
  }

  deleteTable(tableId: number) {
    this.adminService.deleteTable(tableId).subscribe((res: any) => {
      this.viewModalResponse(res.description, AdminViewContainerService.successMessageType, false);
      this.getAllTables(this.pageIndex);

    }, error => {
      console.log(error);

      if (error.error.description) {
        this.viewModalResponse(error.error.description, AdminViewContainerService.dangerMessageType, false);
      } else {
        this.viewModalResponse(error.statusText, AdminViewContainerService.dangerMessageType, false);
      }
    }
    );
  }
  addTable(table: IRestaurantTable) {
    this.adminService.addTable(table).subscribe((res: any) => {
      this.viewModalResponse(res.description, AdminViewContainerService.successMessageType, false);
    }, error => {
      console.log(error);

      if (error.error.description) {
        this.viewModalResponse(error.error.description, AdminViewContainerService.dangerMessageType, false);
      } else {
        this.viewModalResponse(error.statusText, AdminViewContainerService.dangerMessageType, false);
      }
    }

    );

  }
}

import { Injectable } from '@angular/core';
import { UserviewService } from './userview.service';
import { IRestaurantTable } from '../../model/RestaurantTable';
import { NgbCalendar, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IBookDto } from '../../model/BookDto';
import { UserService } from 'src/app/core/user.service';
import { ModalWindowComponent } from 'src/app/shared/component/modal-window/modal-window.component';

export interface IFilter {
  dateFilter: Date;
  noOfPersons: number;
  pageIndex: number;
  pageSize: number;
}
@Injectable({
  providedIn: 'root'
})
export class UserviewContainerService {
  static dangerMessageType = 'danger';
  static successMessageType = 'success';
  hour: number;
  tables: IRestaurantTable[];
  noOfPersons: number;
  bookDate: any;
  filter: IFilter = {} as any;
  pageIndex = 1;
  pageSize = 3;
  today: any;
  bookDto: IBookDto;
  totalElements = 0;
  loading = false;




  constructor(private userviewSerVice: UserviewService, private calendar: NgbCalendar, private userService: UserService,
    private modalService: NgbModal) {
    this.bookDate = this.calendar.getToday();
    this.today = this.calendar.getToday();
    this.bookDto = new IBookDto();

  }


  getAvaiableTables(page: number) {
    this.loading = true;
    page = page - 1;
    this.constructFilter(page);
    this.userviewSerVice.get(this.filter).subscribe((res: any) => {
      this.tables = res.content;
      this.loading = false;
      this.totalElements = res.totalElements;

    }, error => {

      this.loading = false;
      console.log(error);

      if (error.error.description) {
        this.viewModalResponse(error.error.description, UserviewContainerService.dangerMessageType, false);

      } else {
        this.viewModalResponse(error.statusText, UserviewContainerService.dangerMessageType, false);
      }
    });
  }



  bookTable(tableId: number) {
    this.bookDto.tableId = tableId;
    this.bookDto.username = this.userService.currentUser.sub;
    this.bookDto.numberOfPersons = this.filter.noOfPersons;
    this.bookDto.bookDate = this.filter.dateFilter;

    this.userviewSerVice.addBookRequest(this.bookDto).subscribe((res: any) => {
      this.getAvaiableTables(this.pageIndex);
      this.viewModalResponse(res.description, UserviewContainerService.successMessageType, false);

    }, error => {

      console.log(error);
      this.viewModalResponse(error.statusText, UserviewContainerService.dangerMessageType, false);

    }
    );
  }


  constructFilter(page: number) {
    this.filter.pageIndex = page;
    this.filter.dateFilter = new Date(this.bookDate.year, this.bookDate.month - 1, this.bookDate.day);
    this.filter.dateFilter.setHours(this.hour);
    this.filter.noOfPersons = this.noOfPersons;
    this.filter.pageSize = this.pageSize;
  }

  viewModalResponse(message: string, messageType: string, inquiry: boolean) {

    const modalRef = this.modalService.open(ModalWindowComponent);

    modalRef.componentInstance.windowHeader = 'Result';
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.inquiry = inquiry;
    modalRef.componentInstance.messageType = messageType;
  }
}



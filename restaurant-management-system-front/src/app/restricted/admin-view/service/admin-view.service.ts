import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IFilter } from '../../user-view/service/userview-container.service';
import { IRestaurantTable } from '../../model/RestaurantTable';

@Injectable({
  providedIn: 'root'
})
export class AdminViewService {

  constructor(private http: HttpClient) { }

  getTables(pageIndex: number, pageSize: number) {
    return this.http.get(`/api/tables?pageIndex=${pageIndex}&pageSize=${pageSize}`);
  }

  getBookedTable(filter: IFilter) {
    return this.http.get(`/api/bookings?pageIndex=${filter.pageIndex}
    &pageSize=${filter.pageSize}&bookingDate=${filter.dateFilter.toLocaleString()}`);
  }
  addTable(table: IRestaurantTable) {
    return this.http.post('/api/tables', table);
  }

  cancelBooking(bookId: number) {
    return this.http.delete(`/api/bookings/${bookId}`);


  }
  deleteTable(tableId: number) {
    return this.http.delete(`/api/tables/${tableId}`);


  }
}

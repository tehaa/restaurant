import { Injectable } from '@angular/core';
import { IFilter } from './userview-container.service';
import { HttpClient } from '@angular/common/http';
import { IBookDto } from '../../model/BookDto';

@Injectable({
  providedIn: 'root'
})
export class UserviewService {

  constructor(private http: HttpClient) { }

  get(filter: IFilter) {
    return this.http.get(`/api/tables/avaiableTables?pageIndex=${filter.pageIndex}&pageSize=${filter.pageSize}
    &bookingDate=${filter.dateFilter.toLocaleString()}&numberOfPersons=${filter.noOfPersons} `);
  }

  addBookRequest(bookDto: IBookDto) {
    return this.http.post('/api/bookings', bookDto);
  }
}

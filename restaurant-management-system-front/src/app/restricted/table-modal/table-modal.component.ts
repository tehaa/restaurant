import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdminViewService } from '../admin-view/service/admin-view.service';
import { IRestaurantTable } from '../model/RestaurantTable';
import { AdminViewContainerService } from '../admin-view/service/admin-view-container.service';

@Component({
  selector: 'app-table-modal',
  templateUrl: './table-modal.component.html',
  styleUrls: ['./table-modal.component.css']
})
export class TableModalComponent implements OnInit {

  tableRestaurant: IRestaurantTable;
  constructor(private modalService: NgbModal, public activeModal: NgbActiveModal, private adminContaainerService: AdminViewContainerService) {
    this.tableRestaurant = new IRestaurantTable();
  }

  ngOnInit() {
  }

  addTable() {
    this.adminContaainerService.addTable(this.tableRestaurant);
    this.activeModal.close();

  }

}

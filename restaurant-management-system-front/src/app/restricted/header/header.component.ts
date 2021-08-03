import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/core/user.service';
import { ModalWindowComponent } from 'src/app/shared/component/modal-window/modal-window.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private userService: UserService, private modalService: NgbModal) { }

  ngOnInit() {
  }

  logout() {
    this.viewModal('Logout', 'Are You Sure You Want To Logout ?', true);
  }
  viewModal(header: string, message: string, inquiry: boolean) {

    const modalRef = this.modalService.open(ModalWindowComponent);
    modalRef.componentInstance.windowHeader = header;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.inquiry = inquiry;

    modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
      if (receivedEntry === 'yes') {
        localStorage.removeItem('userData');
        this.userService.navigateLogin();
        modalRef.close();
      } else {
        modalRef.close();
      }
    });

  }

}

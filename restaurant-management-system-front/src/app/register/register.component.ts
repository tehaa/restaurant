import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserDto } from '../shared/model/userDto';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../core/user.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalWindowComponent } from '../shared/component/modal-window/modal-window.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  static dangerMessageType = 'danger';
  static successMessageType = 'success';

  loading = false;
  form: FormGroup;
  userDto: UserDto = {
    name: '',
    username: '',
    password: '',
    mobileNumber: ''
  };

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private userService: UserService,
    private modalService: NgbModal) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      name: [null, Validators.required],
      username: [null, Validators.required],
      phone: [null, Validators.required],
      password: [null, Validators.required],
      confirmPassword: ['']
    }, { validator: this.checkPasswords });
  }

  checkPasswords(group: FormGroup) {
    const password = group.get('password').value;
    const confirmPassword = group.get('confirmPassword').value;
    return password === confirmPassword ? null : { notSame: true };
  }

  constructUserDto() {
    this.userDto.name = this.form.get('name').value;
    this.userDto.password = this.form.get('password').value;
    this.userDto.mobileNumber = this.form.get('phone').value;
    this.userDto.username = this.form.get('username').value;

  }

  register() {
    this.loading = true;
    this.constructUserDto();
    console.log('start register user with name ' + this.userDto.name);
    this.http.post('/auth/register', this.userDto).subscribe((response: any) => {
      this.loading = false;
      console.log('user with name ' + this.userDto.name + 'registered successfuly');
      this.viewRegistrationResult(response.description, RegisterComponent.successMessageType, true);


    },
      error => {
        this.loading = false;

        console.log(error);
        if (error.status === 400) {
          if (error.error.description) {
            this.viewRegistrationResult(error.error.description, RegisterComponent.dangerMessageType, false);
          } else {
            this.viewRegistrationResult(error.statusText, RegisterComponent.dangerMessageType, false);
          }
        } else {
          this.viewRegistrationResult(error.statusText, RegisterComponent.dangerMessageType, false);
        }
      });
  }
  viewRegistrationResult(message: string, messageType: string, inquiry: boolean) {

    console.log('Registration Result ....');

    const modalRef = this.modalService.open(ModalWindowComponent);

    modalRef.componentInstance.windowHeader = 'Registration Result';
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.inquiry = inquiry;
    modalRef.componentInstance.messageType = messageType;
    modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
      if (receivedEntry === 'yes') {
        this.userService.navigateLogin();
        modalRef.close();
      } else {
        modalRef.close();
      }
    });
  }

}

import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl
} from '@angular/forms';
import { UserService } from '../core/user.service';
import { ICredential } from '../shared/model/credentials';
import { HttpClient } from '@angular/common/http';
import * as jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  alert = false;
  loading = false;
  form: FormGroup;
  message: string;
  messageType: string;

  credential: ICredential = {
    username: '',
    password: ''
  };

  constructor(private formBuilder: FormBuilder, private userService: UserService, private http: HttpClient) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }
  login() {
    this.loading = true;
    this.alert = false;

    this.credential.username = this.form.get('username').value;
    this.credential.password = this.form.get('password').value;

    this.http.post('/auth/authenticate', this.credential).subscribe((response: any) => {
      let userData: any;
      if (response && response.token) {
        this.loading = false;
        const token = response.token;
        try {
          userData = jwt_decode(token);
        } catch (exc) {
          this.alert = true;
          this.messageType = 'Error';
        }
      } else {
        this.alert = true;
        this.messageType = 'Error';
      }
      this.userService.setUser({ token: response.token, ...userData });
      this.userService.navigateUser();
    },
      error => {
        this.alert = true;
        console.log(error);
        this.loading = false;
        this.messageType = 'Error';
        if (error.error.status === 401) {
          this.message = 'invalid username or password';
        } else {
          this.message = error.statusText;
        }
      }
    );
  }


  register() {
    console.log('start navigate to register service');
    this.userService.navigateRegister();
  }
}

import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { IUser } from '../shared/model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  currentUser: IUser;


  constructor(private route: Router) { 
    this.currentUser = new IUser();
  }

  setUser(user) {
    this.currentUser = user;
    const userData = JSON.stringify(user);
    localStorage.setItem('userData', userData);
  }

  navigateRegister() {
    this.route.navigate(['register']);
  }

  navigateUser() {

    if (this.currentUser.role === 'USER') {
      this.route.navigate(['home/bookTable']);
    } else if (this.currentUser.role === 'ADMIN') {
      this.route.navigate(['home/manageTable']);

    }

  }

  navigateLogin() {
    this.route.navigate(['login']);

  }
}

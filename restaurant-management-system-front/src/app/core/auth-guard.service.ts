import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserService } from './user.service';
import * as jwt_decode from 'jwt-decode';


@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  routesRoles: string[];
  token: string;

  constructor(private userService: UserService) { }
  canActivate(route: ActivatedRouteSnapshot) {

    const user = localStorage.getItem('userData');

    if (user) {
      const object = JSON.parse(user);
      this.token = object.token;
      this.userService.currentUser = jwt_decode(this.token);
      this.routesRoles = route.data.role;
      if (!route.data.roles) {
        return true;
      } else if (this.routesRoles.includes(this.userService.currentUser.role)) {
        return true;
      } else {
        return false;
      }
    } else {
      this.userService.navigateLogin();
      return false;
    }
  }
}

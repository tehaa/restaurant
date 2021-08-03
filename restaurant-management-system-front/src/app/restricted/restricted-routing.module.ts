import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserViewComponent } from './user-view/user-view.component';
import { AdminViewComponent } from './admin-view/admin-view.component';
import { RestrictedComponent } from './restricted.component';


const routes: Routes =

  [{
    path: '',
    component: RestrictedComponent,
    children: [
      { path: 'bookTable', component: UserViewComponent, pathMatch: 'full', data: { role: ['User']} },
      { path: 'manageTable', component: AdminViewComponent, pathMatch: 'full', data: { role: ['ADMIN']} }]
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RestrictedRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RestrictedRoutingModule } from './restricted-routing.module';
import { UserViewComponent } from './user-view/user-view.component';
import { AdminViewComponent } from './admin-view/admin-view.component';
import { SharedModule } from '../shared/shared.module';
import { RestrictedComponent } from './restricted.component';
import { HeaderComponent } from './header/header.component';
import { TableModalComponent } from './table-modal/table-modal.component';


@NgModule({
  declarations: [UserViewComponent, AdminViewComponent, RestrictedComponent, HeaderComponent, TableModalComponent],
  imports: [
    CommonModule,
    RestrictedRoutingModule,SharedModule
  ],
  entryComponents: [TableModalComponent,AdminViewComponent,UserViewComponent],

})
export class RestrictedModule { }

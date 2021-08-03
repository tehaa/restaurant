import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbDatepickerModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormValidationComponent } from './component/form-validation/form-validation.component';
import { AlertComponent } from './component/alert/alert.component';
import { ModalWindowComponent } from './component/modal-window/modal-window.component';


@NgModule({
  declarations: [FormValidationComponent, AlertComponent, ModalWindowComponent],
  imports: [
    CommonModule, FormsModule, ReactiveFormsModule, NgbModule,NgbDatepickerModule
  ],
  entryComponents: [ModalWindowComponent],

  exports: [FormsModule, ReactiveFormsModule, FormValidationComponent, AlertComponent, ModalWindowComponent,NgbDatepickerModule,NgbModule]
})
export class SharedModule { }

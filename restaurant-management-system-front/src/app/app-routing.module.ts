import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuardService } from './core/auth-guard.service';
import { LikeDislikeComponent } from './like-dislike/like-dislike.component';


const routes: Routes = [
  {
    path: 'home', loadChildren: () => import('./restricted/restricted.module').then(m => m.RestrictedModule),
    canActivate: [AuthGuardService], data: { role : ['ADMIN', 'USER']}

  },
  { path: 'login', component: LoginComponent , data: { role : ['anonymous']}},
  { path: 'test', component: LikeDislikeComponent , data: { role : ['anonymous']}},
  { path: 'register', component: RegisterComponent , data: { role : ['anonymous']} },
  { path: '**',  redirectTo: 'login' , pathMatch: 'prefix'}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

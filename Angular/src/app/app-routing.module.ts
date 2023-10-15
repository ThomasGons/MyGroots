import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from '@features/home/home.component';
import { LoginComponent } from '@features/auth/login/login.component';
import { RegisterComponent } from '@features/auth/register/register.component';
import { ContactComponent } from '@features/contact/contact.component';

const routes: Routes = [
  // Routes to home page
  {
    path: "",
    pathMatch: "full",
    redirectTo: "/home"
  },
  {
    path: "home",
    component: HomeComponent,
  },
  // Routes for authentification, login and register
  {
    path: "auth",
    pathMatch: "full",
    redirectTo: "/auth/login"
  },
  {
    path: "auth",
    children: [
      {
        path: "login",
        component: LoginComponent
      },
      {
        path: "register",
        component: RegisterComponent
      }
    ]
  },
  // Route to contact
  {
    path: "contact",
    component: ContactComponent
  },
  // Any other routes that are not matched
  {
    path: "**",
    pathMatch: "full",
    redirectTo: "",
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

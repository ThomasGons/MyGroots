import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { environment } from '@environments/environment.development';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent, RegisterComponent, ForgotPasswordComponent, ChangePasswordComponent } from './features/auth';
import { PageNotFoundComponent } from './features/page-not-found/page-not-found.component';
import { ProfileComponent } from "@app/features/profile/profile.component";


const routes: Routes = [
  {
    path: "",
    pathMatch: "full",
    redirectTo: "/home",
  },
  {
    path: "home",
    component: HomeComponent,
    title: environment.title,
  },
  {
    path: "auth",
    children: [
      {
        path: "login",
        component: LoginComponent,
        title: environment.title + " - Connexion",
      },

      // TODO: this LogoutComponent + AuthGuard 
      // {
      //   path: "logout",
      //   component: LogoutComponent,
      // },

      {
        path: "register",
        component: RegisterComponent,
        title: environment.title + " - Inscription",
      },
      {
        path: "forgot-password",
        component: ForgotPasswordComponent,
        title: environment.title + " - Mot de passe oublié",
      },
      {
        path: "change-password/:token",
        component: ChangePasswordComponent,
        title: environment.title + " - Modifier mot de passe",
      },
    ]
  },

  // TODO: this FamilyTreeComponent + AuthGuard
  // {
  //   path: "family-tree/:id",
  //   component: FamilyTreeComponent,
  //   title: environment.title + " - Arbre Familial",
  // },

  // TODO: this SearchComponent +  children routes (byid, byname)
  // {
  //   path: "search",
  //   component: SearchComponent,
  //   title: environment.title + " - Recherche",
  //   children: []
  // },

  // TODO: AuthGuard
  {
    path: "profile/:id",
    component: ProfileComponent,
    title: environment.title + " - Mon profil",
  },
  {
    path: "**",
    pathMatch: "full",
    component: PageNotFoundComponent,
    title: environment.title,
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

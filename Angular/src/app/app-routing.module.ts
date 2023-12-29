import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { environment } from '@environments/environment.development';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent, RegisterComponent, ForgotPasswordComponent, ChangePasswordComponent, LogoutComponent } from './features/auth';
import { PageNotFoundComponent } from './features/page-not-found/page-not-found.component';
import { ProfileComponent, NotificationsComponent } from './features/user';
import { SearchComponent } from './features/search/search.component';
import { FamilyTreeComponent } from './features/family-tree/family-tree.component';


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
      // TODO: AuthGuard to access page
      {
        path: "logout",
        component: LogoutComponent,
      },
    ]
  },
  // TODO: this FamilyTreeComponent + AuthGuard to access page
  {
    path: "family-tree",
    component: FamilyTreeComponent,
    title: environment.title + " - Arbre Familial",
  },
  // TODO: this SearchComponent +  children routes (byid, byname)
  {
    path: "search",
    component: SearchComponent,
    title: environment.title + " - Recherche",
  //   children: []
  },

  // TODO: AuthGuard to access pages
  {
    path: "user",
    children: [
      {
        path: "profile",
        component: ProfileComponent,
        title: environment.title + " - Profil",
      },
      {
        path: "notifications",
        component: NotificationsComponent,
        title: environment.title + " - Notifications",
      },
    ]
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

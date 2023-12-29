import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderComponent, FooterComponent, SidenavComponent } from './shared/layout';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent, RegisterComponent, LogoutComponent, ForgotPasswordComponent, ChangePasswordComponent, ActivateAccountComponent } from './features/auth';
import { PageNotFoundComponent } from './features/page-not-found/page-not-found.component';
import { ProfileComponent } from './features/user/profile/profile.component';
import { NotificationsComponent } from './features/user/notifications/notifications.component';
import { FamilyTreeComponent } from './features/family-tree/family-tree.component';
import { SearchComponent } from './features/search/search.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SidenavComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent,
    ForgotPasswordComponent,
    ChangePasswordComponent,
    ActivateAccountComponent,
    PageNotFoundComponent,
    ProfileComponent,
    NotificationsComponent,
    FamilyTreeComponent,
    SearchComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

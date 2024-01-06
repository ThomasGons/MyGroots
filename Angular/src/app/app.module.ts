import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxUiLoaderConfig, NgxUiLoaderModule, SPINNER } from 'ngx-ui-loader';

import { AppComponent } from './app.component';
import { HeaderComponent, FooterComponent, SidenavComponent } from './shared/layout';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent, RegisterComponent, LogoutComponent, ForgotPasswordComponent, ChangePasswordComponent, ActivateAccountComponent } from './features/auth';
import { PageNotFoundComponent } from './features/page-not-found/page-not-found.component';
import { NotificationsComponent, ProfileComponent, ProfileModifyComponent } from './features/user';
import { FamilyTreeComponent, TreeAddNodeDialogComponent, TreeRemoveNodeDialogComponent, TreeSearchNodeDialogComponent, ViewOtherFamilyTreeComponent } from './features/family-tree';
import { SearchComponent, SearchResultComponent } from './features/search';


const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  text: "Chargement...",
  textColor: "#FFFFFF",
  textPosition: "center-center",
  bgsColor: "#3C8632",
  fgsColor: "#3C8632",
  fgsType: SPINNER.squareJellyBox,
  fgsSize: 100,
  hasProgressBar: false,
};


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
    NotificationsComponent,
    ProfileComponent,
    ProfileModifyComponent,
    FamilyTreeComponent,
    SearchComponent,
    SearchResultComponent,
    TreeAddNodeDialogComponent,
    TreeRemoveNodeDialogComponent,
    ViewOtherFamilyTreeComponent,
    TreeSearchNodeDialogComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

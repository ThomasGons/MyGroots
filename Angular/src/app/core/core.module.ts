import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './layout/header/header.component';
import { FooterComponent } from './layout/footer/footer.component';
import { AuthComponent } from './auth/auth.component';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    AuthComponent
  ],
  imports: [
    CommonModule
  ]
})
export class CoreModule { }

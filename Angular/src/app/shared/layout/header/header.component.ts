import { Component } from '@angular/core';
import { environment } from '@environments/environment.development';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {

  title: string = environment.title;
  isLoggedIn: boolean = false;

  constructor() {}
  
}

import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  link: string = "/";
  logo: string = "/assets/images/mygroots-logo-gradient.png";
  navItems = [
    // TMP: to change
    { title: "Test login", link: "/auth/login" },
    { title: "Test register", link: "/auth/register" },
  ]
}

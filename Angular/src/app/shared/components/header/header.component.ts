import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  @Input()
  title!: string;

  logoPath: string = "/assets/images/mygroots-logo.png";

  // TMP navItems -> to change
  navItems = [
    { title: "Test login", link: "/auth/login" },
    { title: "Test register", link: "/auth/register" },
    { title: "Test contact", link: "/contact" },
  ]
}

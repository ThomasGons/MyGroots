import { Component } from '@angular/core';
import { environment } from '@environments/environment.development';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent{

  readonly title: string = environment.title;
  readonly navItems = {
      nav: [
        { name: "Arbre Familial", link: "/family-tree" },
        { name: "Recherche", link: "/search" },
      ],
      user: [
        {name: "Profil", link: "/user/profile"},
        {name: "Notifications", link: "/user/notifications"},
        {name: "Deconnexion", link: "/auth/logout"},
      ]
  }
}

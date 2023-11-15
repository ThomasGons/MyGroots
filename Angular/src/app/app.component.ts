import { Component } from '@angular/core';
import { environment } from '@environments/environment.development';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent{

  readonly title: string = environment.title;
  readonly navItems = [
    { name: "Arbre Familial", link: "/family-tree" },
    { name: "Recherche", link: "/search" },
  ]

}

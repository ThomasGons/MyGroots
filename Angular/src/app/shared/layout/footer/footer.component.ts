import { Component } from '@angular/core';
import { environment } from '@environments/environment.development';


@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
})
export class FooterComponent {

  readonly title: string = environment.title;
  readonly currentYear: string = new Date().getFullYear().toString();

}

import { Component, Input } from '@angular/core';
import { User } from '@app/core/models';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
})
export class SearchResultComponent {
  
  @Input()
  user!: User;
}

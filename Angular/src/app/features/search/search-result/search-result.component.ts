import { Component, Input } from '@angular/core';
import { Gender } from '@app/core/models';


@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
})
export class SearchResultComponent {
  
  @Input()
  account!: any;

  readonly genders: any = [
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ];

  public getGender(): string {
    for (let gender of this.genders) {
      if (gender.value == this.account.person.gender) {
        return gender.viewValue;
      }
    }
    return "";
  }

}

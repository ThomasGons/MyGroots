import { Component, Input } from '@angular/core';
import { Gender } from '@app/core/models';


@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
})
export class SearchResultComponent {

  @Input()
  targetAccount!: any;

  @Input()
  sourceAccount!: any;

  @Input()
  targetPerson!: any;

  readonly genders: any = [
    { value: Gender.MALE, viewValue: "Masculin" },
    { value: Gender.FEMALE, viewValue: "Féminin" },
  ];

  protected getGender(): string {
    for (let gender of this.genders) {
      if (gender.value == this.targetAccount?.person?.gender || gender.value == this.targetPerson?.gender) {
        return gender.viewValue;
      }
    }
    return "";
  }

}

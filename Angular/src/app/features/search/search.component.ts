import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { User } from '@app/core/models';
import { StorageService } from '@app/core/services';
import { SearchService } from '@app/core/services/search.service';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ["./search.component.css"]
})
export class SearchComponent {

  formByName = new FormGroup({
    firstName: new FormControl(""),
    lastName: new FormControl(""),
    birthDate: new FormControl(""),
  });
  formById = new FormGroup({
    accountId: new FormControl(""),
  });
  showResults: boolean = false;
  searchResults: any[] = [];
  user!: User;

  constructor(
    private _storageService: StorageService,
    private _searchService: SearchService,
  ) {
    this.user = this._storageService.getUser();
  }

  public onSubmitByName(): void {
    /* Get form data */
    const firstName = !this.formByName.value.firstName ? "" : this.formByName.value.firstName;
    const lastName = !this.formByName.value.lastName ? "" : this.formByName.value.lastName;
    const birthDate = !this.formByName.value.birthDate ? "" : this.formatBirthDate(String(this.formByName.value.birthDate));;
    if (!firstName && !lastName && !birthDate ) {
      return;
    }
    console.log(firstName, lastName, birthDate);
    /* Send form */
    this._searchService.searchByName(firstName, lastName, birthDate).subscribe({
      next: (response) => {
        console.log(response);
        this.searchResults = response.body;
        if (!this.showResults) {
          this.toggleResultsDisplay();
        }
      },
      error: (err) => {
        console.log(err);
        this.searchResults = [];
      }
    });
  }

  public onSubmitById(): void {
    /* Get form data */
    const accountId = !this.formById.value.accountId ? "" : this.formById.value.accountId;
    if (!accountId) {
      return;
    } 
    console.log(accountId);
    /* Send form */
    this._searchService.searchById(accountId).subscribe({
      next: (response) => {
        console.log(response);
        this.searchResults = [response.body];
        if (!this.showResults) {
          this.toggleResultsDisplay();
        }
      },
      error: (err) => {
        console.log(err);
        this.searchResults = []
      }
    });
  }

  public toggleResultsDisplay(): void {
    this.showResults = !this.showResults;
  }

  public cancelForm(type: string): void {
    if (type == "name") {
      this.formByName.reset();
    }
    if (type == "id") {
      this.formById.reset();
    }
    this.clearResults();
  }

  public clearResults(): void  {
    this.showResults = false;
    this.searchResults = [];
  }

  private formatBirthDate(inputDate: string): string {
    const dateObject = new Date(inputDate);
    const year = dateObject.getFullYear();
    const month = (dateObject.getMonth() + 1).toString().padStart(2, '0');
    const day = dateObject.getDate().toString().padStart(2, '0');
    const formattedDate = year+"-"+month+"-"+day;
    return formattedDate;
  }
}

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
  formByCommunId = new FormGroup({
    target_id: new FormControl(""),
  });
  showResults: boolean = false;
  searchResults: any[] = [];
  searchSame: any[] = [];
  searchProbablySame: any[] = [];
  user: User = {};
  src_acc_id: string = '';

  private _snackbarService: any;

  constructor(
    private _searchService: SearchService,
    private _storageService : StorageService,
  ) {}

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
        this.searchResults = response.body.reverse();
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

  public clearResults(): void {
    this.showResults = false;
    this.searchResults = [];
    this.searchProbablySame = [];
    this.searchSame = [];
  }

  public communSearch():void{
    /* Get form data */
    const target_id = !this.formByCommunId.value.target_id ? "" : this.formByCommunId.value.target_id;
    if (!target_id) {
      return;
    }
    console.log(target_id);

    this.user = this._storageService.getUser();
    this.src_acc_id = String(this.user.id);
    this._searchService.searchCommun(String(this.src_acc_id), String(target_id)).subscribe({
      next: (response) => {
        console.log(response);
        this.searchSame = response.body.same;
        this.searchProbablySame = response.body.probably_same;
        console.log(this.searchResults);
        if (!this.showResults) {
          this.toggleResultsDisplay();
        }
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      }
    });

  }

  public toggleResultsDisplay(): void {
    this.showResults = !this.showResults;
  }


  private formatBirthDate(inputDate: string): string {
    const dateObject = new Date(inputDate);
    const year = dateObject.getFullYear();
    const month = (dateObject.getMonth() + 1).toString().padStart(2, '0');
    const day = dateObject.getDate().toString().padStart(2, '0');
    const formattedDate = year+"-"+month+"-"+day;
    return formattedDate;
  }

  protected readonly length = length;
}

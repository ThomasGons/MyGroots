import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { User } from '@app/core/models';
import { SnackbarService, StorageService } from '@app/core/services';
import { SearchService } from '@app/core/services/search.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';


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
  src_acc_id: string = "";

  constructor(
    private _ngxService: NgxUiLoaderService,
    private _snackbarService: SnackbarService,
    private _storageService: StorageService,
    private _searchService: SearchService,
  ) {
    this.user = this._storageService.getUser();
  }

  protected onSubmitByName(): void {
    this._ngxService.start();
    /* Get form data */
    const firstName = !this.formByName.value.firstName ? "" : this.formByName.value.firstName;
    const lastName = !this.formByName.value.lastName ? "" : this.formByName.value.lastName;
    const birthDate = !this.formByName.value.birthDate ? "" : this.formatBirthDate(String(this.formByName.value.birthDate));;
    if (!firstName && !lastName && !birthDate ) {
      return;
    }
    /* Send form */
    this._searchService.searchByName(firstName, lastName, birthDate).subscribe({
      next: (response) => {
        console.log(response);
        this._ngxService.stop();
        this.searchResults = response.body;
        if (!this.showResults) {
          this.toggleResultsDisplay();
        }
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this.searchResults = [];
      }
    });
  }

  protected onSubmitById(): void {
    /* Get form data */
    const accountId = !this.formById.value.accountId ? "" : this.formById.value.accountId;
    if (!accountId) {
      return;
    }
    this._ngxService.start();
    /* Send form */
    this._searchService.searchById(accountId).subscribe({
      next: (response) => {
        console.log(response);
        this._ngxService.stop();
        this.searchResults = [response.body];
        if (!this.showResults) {
          this.toggleResultsDisplay();
        }
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this.searchResults = []
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
  }

  protected toggleResultsDisplay(): void {
    this.showResults = !this.showResults;
  }

  protected cancelForm(type: string): void {
    if (type == "name") {
      this.formByName.reset();
    }
    if (type == "id") {
      this.formById.reset();
    }
    this.clearResults();
  }

  protected clearResults(): void  {
    this.showResults = false;
    this.searchResults = [];
    this.searchProbablySame = [];
    this.searchSame = [];
  }

  protected onSearchCommom():void{
    /* Get form data */
    const target_id = !this.formByCommunId.value.target_id ? "" : this.formByCommunId.value.target_id;
    if (!target_id) {
      return;
    }
    this._ngxService.start();
    this.user = this._storageService.getUser();
    this.src_acc_id = String(this.user.id);
    this._searchService.searchCommon(String(this.src_acc_id), String(target_id)).subscribe({
      next: (response) => {
        console.log(response);
        this.searchSame = response.body.same;
        this.searchProbablySame = response.body.probably_same;
        console.log(this.searchResults);
        this._ngxService.stop();
        if (!this.showResults) {
          this.toggleResultsDisplay();
        }
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
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

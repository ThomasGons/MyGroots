import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from '@app/core/models';
import { FamilyTreeService, SnackbarService, StorageService } from '@app/core/services';
import { NgxUiLoaderService } from 'ngx-ui-loader';


@Component({
  selector: 'app-tree-search-node-dialog',
  templateUrl: './tree-search-node-dialog.component.html',
})
export class TreeSearchNodeDialogComponent {
  
  formSearchByRelation = new FormGroup({
    relation: new FormControl("", {nonNullable: true, validators: [Validators.required]}),
  });
  user!: User;
  showResults: boolean = false;
  searchResults: any[] = [];

  readonly relations: any = [
    { value: "father", viewValue: "Père" },
    { value: "mother", viewValue: "Mère" },
    { value: "partner", viewValue: "Partenaire" },
    { value: "parents", viewValue: "Parents" },
    { value: "children", viewValue: "Enfants" },
    { value: "siblings", viewValue: "Frères et Soeurs" },
    { value: "grandparents", viewValue: "Grands-Parents" },
    { value: "grandchildren", viewValue: "Petits Enfants" },
    { value: "cousins", viewValue: "Cousins" },
    { value: "uncles_ants", viewValue: "Oncles et Tantes" },
    { value: "nephews_nieces", viewValue: "Neveux et Nièces" },
  ];

  constructor(
    private _ngxService: NgxUiLoaderService,
    private _familytreeService: FamilyTreeService,
    private _snackbarService: SnackbarService,
    private _storageService : StorageService,
    public dialogRef: MatDialogRef<TreeSearchNodeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { sourceNodeData: any }
  ) {}

  protected onSubmit(): void{
    this.user = this._storageService.getUser();
    this._ngxService.start();
    /* Get form data */
    const formData = {
      ownerId: this.user.id,
      srcId: this.data.sourceNodeData.id,
      relation: this.formSearchByRelation.value.relation,
    };
    /* Send request */
    this._familytreeService.searchNodeRelation(String(formData.srcId), String(formData.relation), String(formData.ownerId)).subscribe({
      next: (response) => {
        console.log(response);
        this.searchResults = response.body;
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

  protected toggleResultsDisplay(): void {
    this.showResults = !this.showResults;
  }

}

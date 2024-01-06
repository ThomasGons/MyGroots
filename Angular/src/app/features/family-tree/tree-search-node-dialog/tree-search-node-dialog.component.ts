import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '@app/core/models';
import {FamilyTreeService, SnackbarService, StorageService } from '@app/core/services';

@Component({
  selector: 'app-tree-search-node-dialog',
  templateUrl: './tree-search-node-dialog.component.html',
  styleUrls: ['./tree-search-node-dialog.component.css']
})
export class TreeSearchNodeDialogComponent {

  user: User={};

  formSearch = new FormGroup({
    src_id: new FormControl("", {nonNullable: true, validators: [Validators.required]}),
    relation: new FormControl("", {nonNullable: true, validators: [Validators.required]}),
  });

  readonly relations: any = [
    { value: "father", viewValue: "Pere" },
    { value: "mother", viewValue: "Mere" },
    { value: "partner", viewValue: "Partenaire" },
    { value: "parents", viewValue: "Parents" },
    { value: "children", viewValue: "Enfants" },
    { value: "siblings", viewValue: "Frères et Soeurs" },
    { value: "grandparents", viewValue: "Grands-Parents" },
    { value: "grandchildren", viewValue: "Petits Enfants" },
    { value: "cousins", viewValue: "Cousins" },
    { value: "uncles_ants", viewValue: "Oncles et Tantes" },
    { value: "nephews_nieces", viewValue: "Neuveux et Nièces" },
  ]


  constructor(
    private _snackbarService: SnackbarService,
    private _familytreeService: FamilyTreeService,
    private _storageService : StorageService,
  ) { }


  public searchOnSubmit(): void{
    this.user = this._storageService.getUser();
    const src_id = !this.formSearch.value.src_id ? "" : this.formSearch.value.src_id;
    if (!src_id) {
      return;
    }
    const relation = !this.formSearch.value.relation ? "" : this.formSearch.value.relation;
    if (!relation) {
      return;
    }

    this._familytreeService.searchNodeRelation(String(src_id), String(relation), String(this.user.id)).subscribe({
      next: (response) => {
        console.log(response);
        this._snackbarService.openSnackbar(response.message);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
  }


}

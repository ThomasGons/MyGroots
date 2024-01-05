import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-tree-search-node-dialog',
  templateUrl: './tree-search-node-dialog.component.html',
  styleUrls: ['./tree-search-node-dialog.component.css']
})
export class TreeSearchNodeDialogComponent {

  formSearch = new FormGroup({
    firstName: new FormControl("", {nonNullable: true, validators: [Validators.required]}),
    lastName: new FormControl("", {nonNullable: true, validators: [Validators.required]}),
    relation: new FormControl("", {nonNullable: true, validators: [Validators.required]}),
  });

  readonly relations: string[] = [
    "Pere",
    "Mere",
    "fils",
    "fille",
    "grand-père",
    "grand-mère",
  ];


  public submit(){}


}

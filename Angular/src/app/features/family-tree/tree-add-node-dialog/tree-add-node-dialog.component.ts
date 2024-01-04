import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-tree-add-node-dialog',
  templateUrl: './tree-add-node-dialog.component.html',
  styleUrls: ["./tree-add-node-dialog.component.css"]
})
export class TreeAddNodeDialogComponent {

  formAddByName = new FormGroup({
    relation: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    firstName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    lastName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    birthDate: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    gender: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });
  formAddById = new FormGroup({
    relation: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    accountId: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });

  readonly genders: any = [
    { value: "MALE", viewValue: "Homme" },
    { value: "MALE", viewValue: "Femme" },
  ];
  readonly relations: any = [
    {value: "FATHER", viewValue: "Père"},
    {value: "MOTHER", viewValue: "Mère"},
    {value: "PARTNER", viewValue: "Partenaire"},
    {value: "CHILD", viewValue: "Enfant"},
  ];

  constructor(
    public dialogRef: MatDialogRef<TreeAddNodeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {type: string, selectedNodeData: any, nodes: any[]},
  ) {}
  
  public onSubmitAddByName(): void {
    console.log(this.formAddByName.value)
  }

  public onSubmitAddById(): void {
    console.log(this.formAddById.value)
  }

  public matchingRelationAndGender(type: string): void {
    if (type == "byName") {
      const relation = this.formAddByName.value.relation;
      switch (relation) {
        case "FATHER":
          if (!this.formAddByName.controls.gender.disabled) {
            this.formAddByName.controls.gender.patchValue("MALE");
            this.formAddByName.controls.gender.disable();
          }
          this.formAddByName.controls.gender.enable();
          this.formAddByName.controls.gender.patchValue("");
          break;
        case "MOTHER":
          if (!this.formAddByName.controls.gender.disabled) {
            this.formAddByName.controls.gender.patchValue("FEMALE");
            this.formAddByName.controls.gender.disable();
          }
          this.formAddByName.controls.gender.enable();
          this.formAddByName.controls.gender.patchValue("");

          break;
        default: break;
      }
    }
    if (type == "byId") {
      const relation = this.formAddById.value.relation;

    }
    
  }

}

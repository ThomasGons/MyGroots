import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Gender } from '@app/core/models';


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
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ];
  readonly relations: any = [
    {value: "FATHER", viewValue: "Père"},
    {value: "MOTHER", viewValue: "Mère"},
    {value: "PARTNER", viewValue: "Partenaire"},
    {value: "CHILD", viewValue: "Enfant"},
  ];

  constructor(
    public dialogRef: MatDialogRef<TreeAddNodeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {ownerId: string, selectedNodeId: any, selectedNodeData: any, nodes: any[], members: any[]},
  ) {}
  
  public onSubmitAddByName(): void {
    /* Validate form */
    this.formAddByName.markAllAsTouched();
    if (!this.formAddByName.valid) {
      return;
    }
    /* Get form data */
    const formData = {
      ownerId: this.data.ownerId, // accountID of owner of the tree
      srcId: this.data.members[this.data.selectedNodeId].id,   // personID of node selected to add a node to 
      relation: this.formAddByName.value.relation,
      gender: this.formAddByName.value.gender,
      firstName: this.formAddByName.value.firstName,
      lastName: this.formAddByName.value.lastName,
      birthDate: this.formatBirthDate(String(this.formAddByName.value.birthDate)),
    }
    /* Return data to parent component */
    this.dialogRef.close({type: "name", formData: formData});
  }

  public onSubmitAddById(): void {
    /* Validate form */
    this.formAddByName.markAllAsTouched();
    if (!this.formAddByName.valid) {
      return;
    }
    /* Get form data */
    const formData = {
      ownerId: this.data.ownerId, // accountID of owner of the tree
      srcId: this.data.members[this.data.selectedNodeId].id,   // personID of node selected to add a node to
      accountId: this.formAddById.value.accountId,
    }
    this.dialogRef.close({type: "id", formData: formData});
  }


  public matchingRelationAndGender(type: string): void {
    if (type == "byName") {
      const relation = this.formAddByName.value.relation;
      console.log(relation);
    }
    if (type == "byId") {
      const relation = this.formAddById.value.relation;
      console.log(relation);
    }
  }

  private formatBirthDate(inputDate: string): string {
    /* Format the input date to YYYY-MM-DD string */
    const dateObject = new Date(inputDate);
    const year = dateObject.getFullYear();
    const month = (dateObject.getMonth() + 1).toString().padStart(2, '0');
    const day = dateObject.getDate().toString().padStart(2, '0');
    const formattedDate = year+"-"+month+"-"+day;
    return formattedDate;
  }

}

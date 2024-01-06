import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Gender, Relation } from '@app/core/models';
import { NgxUiLoaderService } from 'ngx-ui-loader';


@Component({
  selector: 'app-tree-add-node-dialog',
  templateUrl: './tree-add-node-dialog.component.html',
  styleUrls: ["./tree-add-node-dialog.component.css"]
})
export class TreeAddNodeDialogComponent {

  formAddByName = new FormGroup({
    relation: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    gender: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    firstName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    lastName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    birthDate: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    nationality: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });
  formAddById = new FormGroup({
    relation: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    accountId: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });
  availableRelations: any[] = [];

  readonly genders: any = [
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ];
  readonly relations: any = [
    {value: Relation.FATHER, viewValue: "Père"},
    {value: Relation.MOTHER, viewValue: "Mère"},
    {value: Relation.PARTNER, viewValue: "Partenaire"},
    {value: Relation.CHILD, viewValue: "Enfant"},
  ];
  readonly nationalities: string[] = [
    "Afghanistan",
    "Afrique du Sud",
    "Albanie",
    "Algérie",
    "Allemagne",
    "Andorre",
    "Angola",
    "Antigua-et-Barbuda",
    "Arabie Saoudite",
    "Argentine",
    "Arménie",
    "Australie",
    "Autriche",
    "Azerbaïdjan",
    "Bahamas",
    "Bahreïn",
    "Bangladesh",
    "Barbade",
    "Belgique",
    "Belize",
    "Bénin",
    "Bhoutan",
    "Biélorussie",
    "Birmanie (Myanmar)",
    "Bolivie",
    "Bosnie-Herzégovine",
    "Botswana",
    "Brésil",
    "Brunei",
    "Bulgarie",
    "Burkina Faso",
    "Burundi",
    "Cambodge",
    "Cameroun",
    "Canada",
    "Cap-Vert",
    "Centrafrique",
    "Chili",
    "Chine",
    "Chypre",
    "Colombie",
    "Comores",
    "Congo (République démocratique)",
    "Congo (République du)",
    "Corée du Nord",
    "Corée du Sud",
    "Costa Rica",
    "Côte d'Ivoire",
    "Croatie",
    "Cuba",
    "Danemark",
    "Djibouti",
    "Dominique",
    "Égypte",
    "Émirats arabes unis",
    "Équateur",
    "Érythrée",
    "Espagne",
    "Estonie",
    "États-Unis",
    "Éthiopie",
    "Fidji",
    "Finlande",
    "France",
    "Gabon",
    "Gambie",
    "Géorgie",
    "Ghana",
    "Grèce",
    "Grenade",
    "Guatemala",
    "Guinée",
    "Guinée équatoriale",
    "Guinée-Bissau",
    "Guyana",
    "Haïti",
    "Honduras",
    "Hongrie",
    "Îles Cook",
    "Îles Marshall",
    "Îles Salomon",
    "Inde",
    "Indonésie",
    "Iran",
    "Iraq",
    "Irlande",
    "Islande",
    "Israël",
    "Italie",
    "Jamaïque",
    "Japon",
    "Jordanie",
    "Kazakhstan",
    "Kenya",
    "Kirghizistan",
    "Kiribati",
    "Koweït",
    "Laos",
    "Lesotho",
    "Lettonie",
    "Liban",
    "Liberia",
    "Libye",
    "Liechtenstein",
    "Lituanie",
    "Luxembourg",
    "Macédoine du Nord",
    "Madagascar",
    "Malaisie",
    "Malawi",
    "Maldives",
    "Mali",
    "Malte",
    "Maroc",
    "Maurice",
    "Mauritanie",
    "Mexique",
    "Micronésie",
    "Moldavie",
    "Monaco",
    "Mongolie",
    "Monténégro",
    "Mozambique",
    "Namibie",
    "Nauru",
    "Népal",
    "Nicaragua",
    "Niger",
    "Nigeria",
    "Niue",
    "Norvège",
    "Nouvelle-Zélande",
    "Oman",
    "Ouganda",
    "Ouzbékistan",
    "Pakistan",
    "Palaos",
    "Palestine",
    "Panama",
    "Papouasie-Nouvelle-Guinée",
    "Paraguay",
    "Pays-Bas",
    "Pérou",
    "Philippines",
    "Pologne",
    "Portugal",
    "Qatar",
    "République dominicaine",
    "République tchèque",
    "Roumanie",
    "Royaume-Uni",
    "Russie",
    "Rwanda",
    "Saint-Christophe-et-Niévès",
    "Saint-Marin",
    "Saint-Vincent-et-les-Grenadines",
    "Sainte-Lucie",
    "Salvador",
    "Samoa",
    "São Tomé-et-Principe",
    "Sénégal",
    "Serbie",
    "Seychelles",
    "Sierra Leone",
    "Singapour",
    "Slovaquie",
    "Slovénie",
    "Somalie",
    "Soudan",
    "Soudan du Sud",
    "Sri Lanka",
    "Suède",
    "Suisse",
    "Suriname",
    "Swaziland",
    "Syrie",
    "Tadjikistan",
    "Tanzanie",
    "Tchad",
    "Thaïlande",
    "Timor oriental",
    "Togo",
    "Tonga",
    "Trinité-et-Tobago",
    "Tunisie",
    "Turkménistan",
    "Turquie",
    "Tuvalu",
    "Ukraine",
    "Uruguay",
    "Vanuatu",
    "Vatican",
    "Venezuela",
    "Viêt Nam",
    "Yémen",
    "Zambie",
    "Zimbabwe",
  ];

  constructor(
    private _ngxService: NgxUiLoaderService,
    public dialogRef: MatDialogRef<TreeAddNodeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {ownerId: string, selectedNodeId: any, selectedNodeData: any, nodes: any[], members: any[]},
  ) {
    const node = this.data.nodes[this.data.selectedNodeId];

    if (node.fid == -1 || (this.data.members[node.fid]?.firstName == "unknown" && this.data.members[node.fid]?.lastName == "unknown")) {
      this.availableRelations.push({value: "FATHER", viewValue: "Père"});
    }
    if (node.mid == -1 || (this.data.members[node.mid]?.firstName == "unknown" && this.data.members[node.mid]?.lastName == "unknown")) {
      this.availableRelations.push({value: "MOTHER", viewValue: "Mère"});
    }
    if (node.pids[0] == -1 || (this.data.members[node.pids[0]]?.firstName == "unknown")) {
      this.availableRelations.push({value: "PARTNER", viewValue: "Partenaire"});
    }
    this.availableRelations.push({value: "CHILD", viewValue: "Enfant"});
  }

  protected onSubmitAddByName(): void {
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
      gender: this.formAddByName.controls.gender.value,
      firstName: this.formAddByName.value.firstName,
      lastName: this.formAddByName.value.lastName,
      birthDate: this.formatBirthDate(String(this.formAddByName.value.birthDate)),
      nationality: this.formAddByName.value.nationality,
    }
    /* Return data to parent component */
    const result = {
      type: "name",
      formData: formData,
    }
    this.dialogRef.close(result);
  }

  protected onSubmitAddById(): void {
    /* Validate form */
    this.formAddById.markAllAsTouched();
    if (!this.formAddById.valid) {
      return;
    }
    /* Get form data */
    const formData = {
      ownerId: this.data.ownerId, // accountID of owner of the tree
      srcId: this.data.members[this.data.selectedNodeId].id,   // personID of node selected to add a node to
      relation: this.formAddById.value.relation,
      accountId: this.formAddById.value.accountId,
    }
    /* Return data to parent component */
    const result = {
      type: "id",
      formData: formData,
    }
    console.log(result);
    this.dialogRef.close(result);
  }

  protected matchingRelationAndGender(): void {
    const relation = this.formAddByName.value.relation;
    if (relation == Relation.FATHER) {
      /* Check for unknown. */
      this.formAddByName.patchValue({gender: Gender.MALE});
      this.formAddByName.controls.gender.disable();
    }
    else if (relation == Relation.MOTHER) {
      this.formAddByName.patchValue({gender: Gender.FEMALE});
      this.formAddByName.controls.gender.disable();
    }
    else if (relation == Relation.PARTNER) {
      const gender = this.data.selectedNodeData.gender;
      if (gender == Gender.MALE) {
        this.formAddByName.patchValue({gender: Gender.FEMALE});
      this.formAddByName.controls.gender.disable();

      }
      else if (gender == Gender.FEMALE) {
        this.formAddByName.patchValue({gender: Gender.MALE});
        this.formAddByName.controls.gender.disable();
      }
    }
    else {
      this.formAddByName.controls.gender.enable();
      this.formAddByName.patchValue({gender: ""});
    }
  }

  protected cancelForm(type: string): void {
    if (type == "name") {
      this.formAddByName.controls.gender.enable();
      this.formAddByName.reset();
    }
    if (type == "id") {
      this.formAddById.reset();
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

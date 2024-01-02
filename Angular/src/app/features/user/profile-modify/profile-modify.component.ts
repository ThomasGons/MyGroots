import {Component, OnInit} from '@angular/core';
import { UserService } from '@app/core/services';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SnackbarService, StorageService } from '@app/core/services';
import { User, Gender } from '@app/core/models';
import { Router } from "@angular/router";


@Component({
  selector: 'app-profile-modify',
  templateUrl: './profile-modify.component.html',
  styleUrls: ['./profile-modify.component.css']
})
export class ProfileModifyComponent implements OnInit {

  form = new FormGroup({
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    firstName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    lastName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    birthDate: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    gender: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    nationality: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    socialSecurityNumber: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(13), Validators.maxLength(13), /* Validators.pattern(""/[12][0-9]{2}(0[1-9]|1[0-2])(2[AB]|[0-9]{2})[0-9]{3}[0-9]{3}([0-9]{2})/") */ ] }),
  });
  user: User = {};
  change: boolean = false;
  isForeigner: boolean = false;
  previousSocialSecurityNumber: string = "";

  readonly genders: any = [
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ]
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
    private _snackbarService: SnackbarService,
    private _router: Router,
    private _userService: UserService,
    private _storageService : StorageService,
  ) { }

  ngOnInit(): void {
    /* Get data */
    this.user = this._storageService.getUser();
    /* Send request */
    this._userService.profile(String(this.user.token), String(this.user.id)).subscribe({
      next: (response) => {
        console.log(response);
        this.user = {
          id: this.user.id,
          token: this.user.token,
          email: response.body.email,
          firstName: response.body.person.firstName,
          lastName: response.body.person.lastName,
          birthDate: response.body.person.birthDate,
          gender: response.body.person.gender,
          nationality: response.body.person.nationality,
          socialSecurityNumber: response.body.person.socialSecurityNumber,
        };
        this.form.patchValue({
          email: this.user.email,
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          birthDate: this.user.birthDate,
          gender: this.user.gender,
          nationality: this.user.nationality,
          socialSecurityNumber: this.user.socialSecurityNumber,
        });
        if (this.user.socialSecurityNumber == "99"){
          this.isForeigner = true;
          this.form.controls.socialSecurityNumber.disable();
        };
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  public onSubmit(): void {
    /* Validate the form */
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    /* Get form data */
    const modifyData = {
      id: this.user.id,
      token: this.user.token,
      email: this.form.value.email,
      firstName: this.form.value.firstName,
      lastName: this.form.value.lastName,
      birthDate: this.formatBirthDate(String(this.form.value.birthDate)),
      gender: this.form.value.gender,
      nationality: this.form.value.nationality,
      socialSecurityNumber: this.isForeigner ? "99" : this.form.value.socialSecurityNumber,
    };
    console.log(modifyData);

    /* Submit form */
    this._userService.profileModify(modifyData).subscribe({
      next: (response) => {
        console.log(response);
        let authUserInStorage = this._storageService.getUser();
        authUserInStorage.firstName = this.user.firstName;
        this._storageService.saveUser(authUserInStorage);
        this._snackbarService.openSnackbar(response.message);
        this._router.navigate(["/user/profile"]);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      },
    });
  }

  public onToggleForeigner(): void {
    this.isForeigner = !this.isForeigner;
    if (this.isForeigner) {
      if (this.form.value.socialSecurityNumber) {
        this.previousSocialSecurityNumber = this.form.value.socialSecurityNumber;
      }
      this.form.patchValue({socialSecurityNumber: "99"});
      this.form.controls.socialSecurityNumber.disable();
    }
    else {
      this.form.controls.socialSecurityNumber.enable();
      this.form.patchValue({socialSecurityNumber: this.previousSocialSecurityNumber});
    }
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


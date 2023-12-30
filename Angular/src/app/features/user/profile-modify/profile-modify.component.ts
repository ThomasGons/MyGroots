import {Component, OnInit} from '@angular/core';
import { UserService } from '@app/core/services/user.service';
import { User } from '@app/core/models/user.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { AuthService, SnackbarService, StorageService } from '@app/core/services';
import { Gender } from '@app/core/models';
import {Router} from "@angular/router";


interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}

@Component({
  selector: 'app-profile-modify',
  templateUrl: './profile-modify.component.html',
  styleUrls: ['./profile-modify.component.css']
})


export class ProfileModifyComponent implements OnInit{

  // @ts-ignore
  user: User;
  change: boolean = false;
  responseMessage: string = "";
  isForeigner: boolean = false;

  constructor(private _authService: AuthService,
              private _snackbarService: SnackbarService,
              private _router: Router,
              private userService: UserService,
              private storageService : StorageService,) { }

  ngOnInit(): void {
    // @ts-ignore
    this.user = this.storageService.getUser();
    this.userService.profile({id: this.user.id}).subscribe({
      next: (response) => {
        console.log(response);
        this.user={firstName: response.firstName,
          lastName: response.lastName,
          id: response.id,
          gender: response.gender,
          nationality: response.nationality,
          socialSecurity: response.socialSecurityNumber,
          birthDate: response.birthDate,
          email: response.email};
        this.form.patchValue({
          email: this.user.email,
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          birthDate: this.user.birthDate,
          gender: this.user.gender,
          nationality: this.user.nationality,
          socialSecurity:this.user.socialSecurity});
      },
      error: (err) => {
        console.log(err);

      }
    });

    if (this.user.socialSecurity == '99'){
      this.isForeigner = true;
      this.form.controls.socialSecurity.disable();
      this.form.controls.socialSecurity.setValue("99");
    }
  }

  tiles: Tile[] = [
    {text: 'One', cols: 2, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 2, rows: 1, color: 'lightgreen'},
    {text: 'Three', cols: 2, rows: 1, color: 'lightpink'},
    {text: 'Four', cols: 2, rows: 1, color: '#DDBDF1'},
  ];

  form = new FormGroup({
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    firstName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    lastName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    birthDate: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    gender: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    nationality: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    socialSecurity: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(13), Validators.maxLength(13), /* Validators.pattern(""/[12][0-9]{2}(0[1-9]|1[0-2])(2[AB]|[0-9]{2})[0-9]{3}[0-9]{3}([0-9]{2})/") */ ] }),
  });


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

  public onSubmit(): void {
    /* Validate the form */
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    /* Get form data */
    // const date = new Date(String(this.form.get("birthDate")?.value));
    // const dateString = date.getFullYear().toString() + "-" + date.getMonth().toString() + "-" + date.getDate().toString();
    const modifyData = {
      email: this.form.get("email")?.value,
      firstName: this.form.get("firstName")?.value,
      lastName: this.form.get("lastName")?.value,
      birthDate: this.form.get("birthDate")?.value, // ou dateString
      gender: this.form.get("gender")?.value,
      nationality: this.form.get("nationality")?.value,
      socialSecurity: this.form.get("socialSecurity")?.value,
    };
    /* Submit form */
    this.userService.modify(modifyData).subscribe({
      next: (response) => {
        this.responseMessage = response.message;
        this._snackbarService.openSnackbar(this.responseMessage);
        this._router.navigate(["/user/profile"]);
      },
      error: (err) => {
        this.responseMessage = err.error.message;
        this._snackbarService.openSnackbar(this.responseMessage);
      },
    });
  }

  public onToggleForeigner(): void {
    this.isForeigner = !this.isForeigner;
    if (this.isForeigner) {
      this.form.controls.socialSecurity.disable();
      this.form.controls.socialSecurity.setValue("99");
    }
    else {
      this.form.controls.socialSecurity.enable();
      this.form.controls.socialSecurity.setValue("");
    }
  }


}


import { Component, KeyValueDiffers } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService, SnackbarService } from '@app/core/services';
import { Gender } from '@app/core/models';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['.././auth.css'],
})
export class RegisterComponent {
  
  form = new FormGroup({
    email: new FormControl("", { nonNullable: true, validators: [Validators.required, Validators.email] }),
    firstName: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
    lastName: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
    birthDate: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
    gender: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
    nationality: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
    socialSecurity: new FormControl("", { nonNullable: true, validators: [Validators.required, Validators.minLength(13), Validators.maxLength(13), /* Validators.pattern(""/[12][0-9]{2}(0[1-9]|1[0-2])(2[AB]|[0-9]{2})[0-9]{3}[0-9]{3}([0-9]{2})/") */ ] }),
  });
  isForeigner: boolean = false;

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
    private _authService: AuthService,
    private _snackbarService: SnackbarService,
    private _router: Router,
  ) {}

  public onSubmit(): void {
    /* Validate the form */
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    /* Get form data */
    const date = new Date(String(this.form.get("birthDate")?.value));
    const formattedDate = this.formatBirthDate(date);
    const registerData = {
      email: this.form.get("email")?.value,
      firstName: this.form.get("firstName")?.value,
      lastName: this.form.get("lastName")?.value,
      birthDate: formattedDate,
      gender: this.form.get("gender")?.value,
      nationality: this.form.get("nationality")?.value,
      socialSecurityNumber: this.form.get("socialSecurityNumber")?.value,
    };
    /* Submit form */
    this._authService.register(registerData).subscribe({
      next: (response) => {
        console.log(response);
        this._snackbarService.openSnackbar(response.message);
        this._router.navigate(["/auth/login"]);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.errorMessage);
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

  private formatBirthDate(date: Date): String {
    let formatted: String = "";
    const year = date.getFullYear().toString();
    let month = date.getMonth().toString();
    let day = date.getDay().toString();
    if (month.length < 2) {
      month = "0" + month;
    }
    if (day.length < 2) {
      day = "0" + day;
    }
    formatted = year + "-" + month + "-" + day;
    console.log(formatted);
    return formatted;
  }

}

import {Component, OnInit} from '@angular/core';
import { UserService } from '@app/core/services/user.service';
import { User } from '@app/core/models/user.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { AuthService, SnackbarService } from '@app/core/services';
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

  constructor(private userService: UserService,
  private _authService: AuthService,
  private _snackbarService: SnackbarService,
  private _router: Router,) { }

  ngOnInit(): void {
    this.user = this.userService.getUser();
    if (this.userService.getUser().socialSecurity == '99'){
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
    email: new FormControl(this.userService.getUser().email, { nonNullable: true, validators: [Validators.required, Validators.email] }),
    firstName: new FormControl(this.userService.getUser().firstName, { nonNullable: true, validators: [Validators.required] }),
    lastName: new FormControl(this.userService.getUser().lastName, { nonNullable: true, validators: [Validators.required] }),
    birthDate: new FormControl(this.userService.getUser().birthDate, { nonNullable: true, validators: [Validators.required] }),
    gender: new FormControl(this.userService.getUser().gender, { nonNullable: true, validators: [Validators.required] }),
    nationality: new FormControl(this.userService.getUser().nationality, { nonNullable: true, validators: [Validators.required] }),
    socialSecurity: new FormControl(this.userService.getUser().socialSecurity, { nonNullable: true, validators: [Validators.required, Validators.minLength(13), Validators.maxLength(13), /* Validators.pattern(""/[12][0-9]{2}(0[1-9]|1[0-2])(2[AB]|[0-9]{2})[0-9]{3}[0-9]{3}([0-9]{2})/") */ ] }),
  });


  readonly genders: any = [
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ]

  public onSubmit(): void {
    /* Validate the form */
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    /* Get form data */
    // const date = new Date(String(this.form.get("birthDate")?.value));
    // const dateString = date.getFullYear().toString() + "-" + date.getMonth().toString() + "-" + date.getDate().toString();
    const registerData = {
      email: this.form.get("email")?.value,
      firstName: this.form.get("firstName")?.value,
      lastName: this.form.get("lastName")?.value,
      birthDate: this.form.get("birthDate")?.value, // ou dateString
      gender: this.form.get("gender")?.value,
      nationality: this.form.get("nationality")?.value,
      socialSecurity: this.form.get("socialSecurity")?.value,
    };
    /* Submit form */
    this._authService.register(registerData).subscribe({
      next: (response) => {
        this.responseMessage = response.message;
        this._snackbarService.openSnackbar(this.responseMessage);
        this._router.navigate(["/auth/login"]);
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


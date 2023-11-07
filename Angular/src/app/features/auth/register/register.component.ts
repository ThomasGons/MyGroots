import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService, SnackbarService } from '@app/core/services';
import { GenderValidator } from '@app/core/validators';
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
    gender: new FormControl("", { nonNullable: true, validators: [Validators.required, GenderValidator] }),
    nationality: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
    socialSecurity: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
  });
  responseMessage: string = "";
  genders = [
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ]

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
    const date = new Date(String(this.form.value.birthDate));
    const dateString = date.getFullYear().toString() + "-" + date.getMonth().toString() + "-" + date.getDate().toString();
    const registerData = {
      email: this.form.value.email,
      firstName: this.form.value.firstName,
      lastName: this.form.value.lastName,
      birthDate: this.form.value.birthDate, // ou dateString
      gender: this.form.value.gender,
      nationality: this.form.value.nationality,
      socialSecurity: this.form.value.socialSecurity,
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

}

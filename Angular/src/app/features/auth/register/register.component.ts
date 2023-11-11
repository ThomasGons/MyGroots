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
    socialSecurity: new FormControl("", { nonNullable: true, validators: [Validators.required, Validators.pattern("/[12][0-9]{2}(0[1-9]|1[0-2])(2[AB]|[0-9]{2})[0-9]{3}[0-9]{3}([0-9]{2})/")] }),
  });
  responseMessage: string = "";
  isForeigner: boolean = false;

  readonly genders: any = [
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

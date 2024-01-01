import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, SnackbarService } from '@app/core/services';


@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['.././auth.css'],
})
export class ForgotPasswordComponent {

  form = new FormGroup({
    email: new FormControl("", { nonNullable: true, validators: [Validators.required, Validators.email] }),
  });

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
    const email = String(this.form.value.email);
    /* Submit form */
    this._authService.forgotPassword(email).subscribe({
      next: (response) => {
        console.log(response);
        const id: string = response.id;
        const token: string = response.token;
        const firstName: string = response.firstName;
        this._snackbarService.openSnackbar(firstName + ", vous allez modifier votre mot de passe...")
        this._router.navigate(["auth/change-password", id, token]);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.errorMessage);
        this.form.controls.email.setValue("");
      }
    })
  }

}

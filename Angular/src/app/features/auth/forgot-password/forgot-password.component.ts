import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
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
  responseMessage: string = "";

  constructor(
    private _authService: AuthService,
    private _snackbarService: SnackbarService,
  ) {}

  public onSubmit(): void {
    /* Validate the form */
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    /* Get form data */
    const forgotPasswordData = {
      email: this.form.value.email,
    }
    /* Submit form */
    this._authService.forgotPassword(forgotPasswordData).subscribe({
      next: (response) => {
        this.responseMessage = response.message;
        this._snackbarService.openSnackbar(this.responseMessage);
      },
      error: (err) => {
        this.responseMessage = err.error.message;
        this._snackbarService.openSnackbar(this.responseMessage);
      }
    })
  }

}

import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService, SnackbarService } from '@app/core/services';
import { NgxUiLoaderService } from 'ngx-ui-loader';


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
    private _ngxService: NgxUiLoaderService,
    private _authService: AuthService,
    private _snackbarService: SnackbarService,
  ) {}

  protected onSubmit(): void {
    /* Validate the form */
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    this._ngxService.start();
    /* Get form data */
    const email = String(this.form.value.email);
    /* Submit form */
    this._authService.forgotPassword(email).subscribe({
      next: (response) => {
        console.log(response);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(response.message);
      },
      error: (err) => {
        console.log(err);
        this.form.controls.email.setValue("");
        this._ngxService.stop();
        this._snackbarService.openSnackbar(err.error.message);
      }
    })
  }

}

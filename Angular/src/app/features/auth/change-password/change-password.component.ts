import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService, SnackbarService } from '@app/core/services';


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['.././auth.css']
})
export class ChangePasswordComponent {

  form = new FormGroup({
    password: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
  });
  hidePassword: boolean = true;
  
  private _id: string;
  private _token: string;
  
  constructor(
    private _authService: AuthService,
    private _snackbarService: SnackbarService,
    private _activatedRoute: ActivatedRoute,
    private _router: Router,
  ) {
    this._id = String(this._activatedRoute.snapshot.paramMap.get("id"));
    this._token = String(this._activatedRoute.snapshot.paramMap.get("token"));
  }

  public onSubmit(): void {
    /* Validate the form */
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    /* Get form data */
    const newPassword = String(this.form.value.password);
    /* Submit form */
    this._authService.changePassword(this._id, this._token, newPassword).subscribe({
      next: (response) => {
        console.log(response);
        this._snackbarService.openSnackbar(response.message);
        this._router.navigate(["/auth/login"]);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.errorMessage);
        this._router.navigate(["/home"]);
      }
    });
  }

}

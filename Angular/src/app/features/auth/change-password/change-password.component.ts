import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService, SnackbarService } from '@app/core/services';


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['.././auth.css']
})
export class ChangePasswordComponent implements OnInit {

  form = new FormGroup({
    password: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
  });
  hidePassword: boolean = true;
  hidePasswordConfirm: boolean = true;
  
  constructor(
    private _authService: AuthService,
    private _snackbarService: SnackbarService,
    private _activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    console.log(this._activatedRoute.snapshot.paramMap.get("token"));
  }
  
  public onSubmit(): void {
    /* Validate the form */
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    /* Get form data */
    const changePasswordData = {
      token: this._activatedRoute.snapshot.paramMap.get("token"),
      password: this.form.value.password,
    }
    /* Submit form */
    this._authService.changePassword(changePasswordData).subscribe({
      // FIX: TMP
      next: (response) => {
        this._snackbarService.openSnackbar(response.message);
      },
      error: (err) => {
        this._snackbarService.openSnackbar(err.error.errorMessage);
      }
    });
  }

}

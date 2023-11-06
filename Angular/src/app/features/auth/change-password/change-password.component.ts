import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService, JwtService, SnackbarService } from '@app/core/services';


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['.././auth.css']
})
export class ChangePasswordComponent implements OnInit {

  form = new FormGroup({
    password: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
    passwordConfirm: new FormControl("", { nonNullable: true, validators: [Validators.required] }),
  });
  hidePassword: boolean = true;
  hidePasswordConfirm: boolean = true;
  
  constructor(
    private _authService: AuthService,
    private _jwtService: JwtService,
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
    if (this.form.value.password !== this.form.value.passwordConfirm) {
      return;
    }
    /* Get form data */
    const changePasswordData = {
      token: this._activatedRoute.snapshot.paramMap.get("token"),
      password: this.form.value.password,
    }
    /* Submit form */
    this._authService.changePassword(changePasswordData).subscribe({
      next: (response) => {

      },
      error: (err) => {

      }
    });
  }

}

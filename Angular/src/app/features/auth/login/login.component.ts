import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService, SnackbarService, StorageService } from '@app/core/services';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['.././auth.css']
})
export class LoginComponent {

  form = new FormGroup({
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });
  responseMessage: string = "";
  hidePassword: boolean = true;

  constructor(
    private _authService: AuthService,
    private _storageService: StorageService,
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
    const loginData = {
      email: this.form.value.email,
      password: this.form.value.password,
    }
    /* Submit the form */
    this._authService.login(loginData).subscribe({
      next: (response) => {
        this._snackbarService.openSnackbar("Connexion réussie !");
        this._storageService.saveUser(response)
        this._router.navigate(["/home"]);
      },
      error: (err) => {
        this.responseMessage = err.message;
        this._snackbarService.openSnackbar(this.responseMessage);
      }
    });
  }

}

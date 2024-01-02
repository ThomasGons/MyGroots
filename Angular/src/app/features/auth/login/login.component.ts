import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService, StorageService, SnackbarService } from '@app/core/services';


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
    const email = String(this.form.value.email);
    const password = String(this.form.value.password);
    /* Submit the form */
    this._authService.login(email, password).subscribe({
      next: (response) => {
        console.log(response);
        this._snackbarService.openSnackbar("Connexion réussie !");
        this._storageService.saveUser(response)
        this._router.navigate(["/home"]);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.errorMessage);
        this.form.controls.email.setValue("");
        this.form.controls.password.setValue("");
      }
    });
  }

}

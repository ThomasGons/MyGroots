import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService, SnackbarService, StorageService } from '@app/core/services';


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {

  constructor(
    private _authService: AuthService,
    private _storageService: StorageService,
    private _snackbarService: SnackbarService,
    private _router: Router,
    private _location: Location,  
  ) {}

  ngOnInit(): void {
    /* Get data */
    const userData = this._storageService.getUser();
    const id = String(userData!.id);
    const token = String(userData!.token);
    /* Send request */
    this._authService.logout(id, token).subscribe({
      next: (response) => {
        console.log(response);
        this._snackbarService.openSnackbar(response.message);
        this._storageService.deleteUser();
        this._router.navigate(["/home"]);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.errorMessage);
        this._location.back();
      }
    })
  }

}

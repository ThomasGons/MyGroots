import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService, SnackbarService, StorageService } from '@app/core/services';
import { NgxUiLoaderService } from 'ngx-ui-loader';


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {

  constructor(
    private _ngxService: NgxUiLoaderService,
    private _authService: AuthService,
    private _storageService: StorageService,
    private _snackbarService: SnackbarService,
    private _router: Router,
    private _location: Location,  
  ) {}

  ngOnInit(): void {
    this._ngxService.start();
    /* Get data */
    const userData = this._storageService.getUser();
    const id = String(userData!.id);
    const token = String(userData!.token);
    /* Send request */
    this._authService.logout(id, token).subscribe({
      next: (response) => {
        console.log(response);
        this._storageService.deleteUser();
        this._ngxService.stop();
        this._snackbarService.openSnackbar(response.message);
        this._router.navigate(["/home"]);
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(err.error.message);
        this._location.back();
      }
    })
  }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService, SnackbarService } from '@app/core/services';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['.././auth.css']
})
export class ActivateAccountComponent implements OnInit {

  private _id: string;

  constructor(
    private _ngxService: NgxUiLoaderService,
    private _authService: AuthService,
    private _snackbarService: SnackbarService,
    private _activatedRoute: ActivatedRoute,
    private _router: Router,
  ) {
    this._id = String(this._activatedRoute.snapshot.paramMap.get("id"));
  }

  ngOnInit(): void {
    this._ngxService.start();
    this._authService.activateAccount(this._id).subscribe({
      next: (response) => {
        console.log(response);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(response.message);
        this._router.navigate(["/home"]);
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(err.error.message);
      }
    })
  }

}

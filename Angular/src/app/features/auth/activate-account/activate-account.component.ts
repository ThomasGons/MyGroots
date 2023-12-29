import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService, SnackbarService } from '@app/core/services';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['.././auth.css']
})
export class ActivateAccountComponent implements OnInit {

  private _token: string;

  constructor(
    private _authService: AuthService,
    private _snackbarService: SnackbarService,
    private _activatedRoute: ActivatedRoute,
    private _router: Router,
  ) {
    this._token = String(this._activatedRoute.snapshot.paramMap.get("token"));
  }

  ngOnInit(): void {
    console.log(this._token);

    this._authService.activateAccount(this._token).subscribe({
      next: (response) => {
        console.log(response);
        this._snackbarService.openSnackbar(response.message);
        this._router.navigate(["/home"]);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      }
    })
  }

}

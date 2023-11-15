import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { AuthService, JwtService, SnackbarService } from '@app/core/services';


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
})
export class SidenavComponent implements OnInit {

  @Input() navItems: any;
  @ViewChild('sidenav') sidenav!: MatSidenav;

  isLoggedIn: boolean = false;

  constructor(
    private _authService: AuthService,
    private _jwtService: JwtService,
    private _snackbarService: SnackbarService,
    private _router: Router,
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this._authService.isAuthenticated();
  }

  public toggle(): void {
    this.sidenav.toggle();
  }

  public logout(): void {
    this._authService.logout().subscribe({
      next: (response) => {
        this._jwtService.destroyToken();
        this._snackbarService.openSnackbar(response.message);
        this._router.navigate(["/home"]);
      },
      error: (err) => {
        this._snackbarService.openSnackbar(err.message);
      }
    });
  }
  
}

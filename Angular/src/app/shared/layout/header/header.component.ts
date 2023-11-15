import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, JwtService, SnackbarService } from '@app/core/services';
import { environment } from '@environments/environment.development';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {

  @Input() navItems: any;
  @Output() signalToggleSidenav = new EventEmitter<void>();

  readonly title: string = environment.title;
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

  public onToggleSidenav(): void {
    this.signalToggleSidenav.emit();
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
      },
    });
  }

}

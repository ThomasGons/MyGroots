import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@app/core/services';


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {

  constructor(
    private _authService: AuthService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    const id = String(this._activatedRoute.snapshot.paramMap.get("id"));
    this._authService.logout(id).subscribe({
      next: (response) => {
        this._router.navigate(["/home"]);
      },
      error: (err) => {

      }
    })
  }

}

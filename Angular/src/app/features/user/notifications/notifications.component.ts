import { Component } from '@angular/core';
import { User } from '@app/core/models';
import { SnackbarService, StorageService, UserService } from '@app/core/services';
import { NgxUiLoaderService } from 'ngx-ui-loader';


@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css'],
})
export class NotificationsComponent {

  user!: User;
  messages!: any[];

  constructor(
    private _ngxService: NgxUiLoaderService,
    private _snackbarService: SnackbarService,
    private _userService: UserService,
    private _storageService : StorageService,
  ) { }

  ngOnInit(): void {
    /* Get data */
    this.user = this._storageService.getUser();
    this._ngxService.start();
    /* Send request */
    this._userService.notifs(String(this.user.token), String(this.user.id)).subscribe({
      next: (response) => {
        console.log(response);
        this.messages = response.body;
        this._ngxService.stop();
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
  }

  protected delete(notifId: string): void {
    this._ngxService.start();
    this._userService.delete(String(notifId)).subscribe({
      next: (response) => {
        console.log(response);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(response.message);
        this.ngOnInit();
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
  }

  protected response(notifId: string, response: boolean): void {
    this._ngxService.start();
    this._userService.response(String(response), String(notifId)).subscribe({
      next: (response) => {
        console.log(response);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(response.message);
        this.ngOnInit();
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
    this.ngOnInit();
  }

}

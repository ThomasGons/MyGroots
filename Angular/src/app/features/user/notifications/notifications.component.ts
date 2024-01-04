import { Component } from '@angular/core';
import { User } from '@app/core/models';
import { SnackbarService, StorageService, UserService } from '@app/core/services';


@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css'],
})
export class NotificationsComponent {

  user: User = {};
  messages: any[] | null = null;

  constructor(
    private _snackbarService: SnackbarService,
    private _userService: UserService,
    private _storageService : StorageService,
  ) { }


  ngOnInit(): void {
    /* Get data */
    this.user = this._storageService.getUser();
    /* Send request */
    this._userService.notifs(String(this.user.token), String(this.user.id)).subscribe({
      next: (response) => {
        console.log(response);
        this.messages = response.body;
        console.log(this.messages);
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
  }
  delete(notifId:string) {
    this._userService.delete(String(notifId)).subscribe({
      next: (response) => {
        console.log(response);
        this._snackbarService.openSnackbar(response.message);
        this.ngOnInit();
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      }
    });

  }

  response(notifId:string, response:boolean) {
    this._userService.response(String(response), String(notifId)).subscribe({
      next: (response) => {
        console.log(response);
        this._snackbarService.openSnackbar(response.message);
        this.ngOnInit();
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
    this.ngOnInit();
  }

}

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
  refuser(id:string) {
    console.log(id);
  }

  accepter(id:string) {
    console.log(id);
  }

}

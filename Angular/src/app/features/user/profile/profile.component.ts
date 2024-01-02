import {Component, OnInit} from '@angular/core';
import {UserService} from '@app/core/services/user.service';
import { SnackbarService, StorageService } from '@app/core/services';
import { User } from '@app/core/models';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {

  user: User = {};

  constructor(
    private _snackbarService: SnackbarService,
    private _userService: UserService,
    private _storageService : StorageService,
  ) { }

  ngOnInit(): void {
    /* Get data */
    this.user = this._storageService.getUser();
    /* Send request */ 
    this._userService.profile(String(this.user.token), String(this.user.id)).subscribe({
      next: (response) => {
        console.log(response);
        this.user = {
          firstName: response.firstName,
          lastName: response.lastName,
          id: response.id,
          gender: response.gender,
          nationality: response.nationality,
          socialSecurityNumber: response.socialSecurityNumber,
          birthDate: response.birthDate,
          email: response.email
        }
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
  }

}

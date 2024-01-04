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
  gender: string = "";

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
          email: response.body.email,
          firstName: response.body.person.firstName,
          lastName: response.body.person.lastName,
          birthDate: response.body.person.birthDate,
          gender: response.body.person.gender,
          nationality: response.body.person.nationality,
          socialSecurityNumber: response.body.person.socialSecurityNumber,
        };
        if (this.user.gender == 'MALE'){
          this.gender = "Homme"
        }else {
          this.gender = "Femme"};
      },
      error: (err) => {
        console.log(err);
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
  }

}

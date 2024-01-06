import {Component, OnInit} from '@angular/core';
import {UserService} from '@app/core/services/user.service';
import { SnackbarService, StorageService } from '@app/core/services';
import { Gender, User } from '@app/core/models';
import { NgxUiLoaderService } from 'ngx-ui-loader';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {

  authUser: User;
  user: User;
  treeVisibility: string = "";

  readonly genders: any = [
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ]

  constructor(
    private _ngxService: NgxUiLoaderService,
    private _snackbarService: SnackbarService,
    private _userService: UserService,
    private _storageService : StorageService,
  ) {
    this.authUser = this._storageService.getUser();
    this.user = this.authUser;
  }

  ngOnInit(): void {
    /* Get data */
    this._ngxService.start();
    /* Send request */
    this._userService.profile(String(this.authUser.token), String(this.authUser.id)).subscribe({
      next: (response) => {
        console.log(response);
        this.user = {
          id: response.body.person.id,
          email: response.body.email,
          firstName: response.body.person.firstName,
          lastName: response.body.person.lastName,
          birthDate: response.body.person.birthDate,
          gender: response.body.person.gender,
          nationality: response.body.person.nationality,
          socialSecurityNumber: response.body.person.socialSecurityNumber,
        };
        this.treeVisibility = response.body.treeVisibility;
        this._ngxService.stop();
      },
      error: (err) => {
        console.log(err);
        this._ngxService.stop();
        this._snackbarService.openSnackbar(err.error.message);
      }
    });
  }

  protected getGender(): string {
    for (let gender of this.genders) {
      if (gender.value == this.user?.gender) {
        return gender.viewValue;
      }
    }
    return "";
  }

}

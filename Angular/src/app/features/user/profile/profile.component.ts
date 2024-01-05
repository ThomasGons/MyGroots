import {Component, OnInit} from '@angular/core';
import {UserService} from '@app/core/services/user.service';
import { SnackbarService, StorageService } from '@app/core/services';
import { Gender, User } from '@app/core/models';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {

  user: User = {};
  treeVisibility: string = "";

  readonly genders: any = [
    { value: "MALE", viewValue: "Homme" },
    { value: "FEMALE", viewValue: "Femme" },
  ]

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
          id: this.user.id,
          email: response.body.email,
          firstName: response.body.person.firstName,
          lastName: response.body.person.lastName,
          birthDate: response.body.person.birthDate,
          gender: response.body.person.gender,
          nationality: response.body.person.nationality,
          socialSecurityNumber: response.body.person.socialSecurityNumber,
        };
        this.treeVisibility = response.body.treeVisibility;
      },
      error: (err) => {
        console.log(err);
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

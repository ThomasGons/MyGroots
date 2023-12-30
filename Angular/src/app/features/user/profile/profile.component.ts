import {Component, OnInit} from '@angular/core';
import {UserService} from '@app/core/services/user.service';
import {Gender, User} from '@app/core/models/user.model';
import { StorageService } from '@app/core/services';


export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})



export class ProfileComponent implements OnInit{

  // @ts-ignore
  user: User;

  constructor(private userService: UserService,
              private storageService : StorageService,) { }

  ngOnInit(): void {
    // @ts-ignore
    this.user = this.storageService.getUser();
    this.userService.profile({id: this.user.id}).subscribe({
      next: (response) => {
        console.log(response);
        this.user={firstName: response.firstName,
        lastName: response.lastName,
        id: response.id,
        gender: response.gender,
        nationality: response.nationality,
        socialSecurity: response.socialSecurityNumber,
        birthDate: response.birthDate,
        email: response.email}
      },
      error: (err) => {
        console.log(err);

      }
    });
  }

  tiles: Tile[] = [
    {text: 'One', cols: 2, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 2, rows: 1, color: 'lightgreen'},
    {text: 'Three', cols: 2, rows: 1, color: 'lightpink'},
    {text: 'Four', cols: 2, rows: 1, color: '#DDBDF1'},
  ];
}





/* Get data */

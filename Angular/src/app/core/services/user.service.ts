import {Injectable} from '@angular/core';
import {Gender, User} from '@app/core/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }
  getUser(): User {
    // Simuler une récupération de données
    return {
      id: '1825',
      token: 'token',
      email: 'ThomasGons@gmail.com',
      firstName: 'Thomas',
      birthDate : '10/11/2002',
      lastName: 'Gons',
      gender: Gender.MALE,
      nationality: 'francais',
      socialSecurity: '1545154845678'
    };
  }

}

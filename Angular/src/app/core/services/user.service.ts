import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment.development'
import {Gender, User} from '@app/core/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly url: string = environment.apiUrl + "/user";
  private readonly httpOptions = {
    headers: new HttpHeaders({ "Content-type": "application/json" })
  };

  constructor(
    private _httpClient: HttpClient,
  ) {}
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
      nationality: 'Francais',
      socialSecurity: '1545154845678'
    };
  }

  public profile(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/profile", data, this.httpOptions);
  }

}

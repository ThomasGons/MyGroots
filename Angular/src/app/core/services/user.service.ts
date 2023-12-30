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

  public profile(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/profile", data, this.httpOptions);
  }

  public modify(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/profile-modify", data, this.httpOptions);
  }

}

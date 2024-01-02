import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment.development'


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

  /**
   * 
   * @param token 
   * @param id 
   */
  public profile(token: string, id: string): Observable<any> {
    return this._httpClient.post(this.url + "/profile", {token: token, id: id}, this.httpOptions);
  }

  /**
   * 
   * @param data 
   */
  public profileModify(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/profile-modify", data, this.httpOptions);
  }

}

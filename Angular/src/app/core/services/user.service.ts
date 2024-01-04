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
   * Send request to server to get a user profile informations, need to be authenticated with a token.
   * @param token Authentication token
   * @param accountId Id of the account
   */
  public profile(token: string, accountId: string): Observable<any> {
    return this._httpClient.post(this.url + "/profile", {token: token, accountId: accountId}, this.httpOptions);
  }

  /**
   * Send request to server to change user profile informations, need to be authenticated with a token.
   * @param data Informations to change and authentication data
   */
  public profileModify(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/profile-modify", data, this.httpOptions);
  }

  /**
   * Send request to server to get all notifications owned by the user, need to be authenticated with a token.
   * @param token Authentication token
   * @param accountId Id of the account
   */
  public notifs(token: string, accountId: string): Observable<any> {
    return this._httpClient.post(this.url + "/notifs", {token: token, accountId: accountId}, this.httpOptions);
  }

  /**
   * Send request to server to get all notifications owned by the user, need to be authenticated with a token.
   * @param token Authentication token
   * @param accountId Id of the account
   */
  public response(response: string, notifId: string): Observable<any> {
    return this._httpClient.post(this.url + "/notifs/response", {reponse: response, notifId: notifId}, this.httpOptions);
  }

  /**
   * Send request to server to get all notifications owned by the user, need to be authenticated with a token.
   * @param token Authentication token
   * @param accountId Id of the account
   */
  public delete(notifId: string): Observable<any> {
    return this._httpClient.post(this.url + "/notifs/delete", {notifId: notifId}, this.httpOptions);
  }
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment.development';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly url: string = environment.apiUrl + "/auth";
  private readonly httpOptions = {
    headers: new HttpHeaders({ "Content-type": "application/json" })
  };

  constructor(
    private _httpClient: HttpClient,
  ) {}

  /**
   * Send request to server to login.
   * @param email Email to login
   * @param password Password to login
   */
  public login(email: string, password: string): Observable<any> {
    return this._httpClient.post(this.url + "/login", {email: email, password: password}, this.httpOptions);
  }

  /**
   * Send request to server to logout, need to be authenticated with a token.
   * @param accountId Id of the account
   * @param token Authentication token
   */
  public logout(accountId: string, token: string): Observable<any> {
    return this._httpClient.post(this.url + "/logout", {id: accountId, token: token}, this.httpOptions);
  }

  /**
   * Send request to server to regitser user to the database.
   * @param data Register form data
   */
  public register(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/register", data, this.httpOptions);
  }

  /**
   * Send request to server to indicate user has forgot his password.
   * @param email Email of the account
   */
  public forgotPassword(email: string): Observable<any> {
    return this._httpClient.post(this.url + "/forgot-password", {email: email}, this.httpOptions);
  }

  /**
   * Send request to server to change account password, need to be authenticated with a token.
   * @param accountId Id of the account
   * @param token Authentication token
   * @param newPassword New password to set
   */
  public changePassword(accountId: string, token: string, newPassword: string): Observable<any> {
    return this._httpClient.put(this.url + "/change-password", {id: accountId, token: token, newPassword: newPassword}, this.httpOptions);
  }
  
  /**
   * Activate an account after the registration.
   * @param accountId Id of the account to be activated
   */
  public activateAccount(accountId: string): Observable<any> {
    return this._httpClient.post(this.url + "/activate-account/" + accountId, {}, this.httpOptions);
  }

}

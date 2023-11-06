import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment.development';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly url: string = environment.apiUrl + "/auth";
  private readonly httpOptions = { headers: new HttpHeaders({ "Content-type": "application/json" }) };

  constructor(
    private _httpClient: HttpClient,
  ) {}

  public login(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/login", data, this.httpOptions);
  }

  public logout(): Observable<any> {
    return this._httpClient.post(this.url + "/logout", {}, this.httpOptions);
  }

  public register(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/register", data, this.httpOptions);
  }
  
  public forgotPassword(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/forgot-password", data, this.httpOptions);
  }
  
}

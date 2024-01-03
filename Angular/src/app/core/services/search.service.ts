import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment.development';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private readonly url: string = environment.apiUrl + "/search";
  private readonly httpOptions = {
    headers: new HttpHeaders({ "Content-type": "application/json" })
  };

  constructor(
    private _httpClient: HttpClient,
  ) {}

  /**
   * Send request to server to get a list of Person matching firstName, lastName or birthDate.
   * @param firstName FirstName to match
   * @param lastName LastName to match
   * @param birthDate BirthDate to match
   */
  public searchByName(firstName: string, lastName: string, birthDate: string): Observable<any> {
    return this._httpClient.post(this.url + "/name", {firstName: firstName, lastName: lastName, birthDate: birthDate}, this.httpOptions);
  }

  /**
   * Send request to server to get a Person by its id.
   * @param personId Id of the Person in database
   */
  public searchById(personId: string): Observable<any> {
    return this._httpClient.post(this.url + "/id", {personId: personId}, this.httpOptions);
  }

}

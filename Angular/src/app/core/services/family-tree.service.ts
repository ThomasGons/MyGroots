import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment.development';


@Injectable({
  providedIn: 'root'
})
export class FamilyTreeService {

  private readonly url: string = environment.apiUrl + "/family-tree";
  private readonly httpOptions = {
    headers: new HttpHeaders({ "Content-type": "application/json" })
  };

  constructor(
    private _httpClient: HttpClient,
  ) {}


  // public getPersonByID(data: any): Observable<any> {
  //   return this._httpClient.post(this.url + "/getPerson", data, this.httpOptions);
  // }

  /**
   * Send request to server to get the tree of the account connected, need authentication with a token
   * @param token Authentication token
   * @param accountId Id of the account
   */
  public getFamilyTreeById(token: string, accountId: string): Observable<any> {
    return this._httpClient.post(this.url + "/", {token: token, accountId: accountId}, this.httpOptions);
  }
  
  /**
   * Send request to server to get the tree of the person.
   * @param personId Id of the Person in database
   */
  public getOtherFamilyTreeById(personId: string): Observable<any> {
    return this._httpClient.post(this.url + "/other", {personId: personId}, this.httpOptions);
  }

  public addNodeByName(data: any): Observable<any> {
    return this._httpClient.put(this.url + "/node/name", data, this.httpOptions);
  }

  public addNodeById(data: any): Observable<any> {
    return this._httpClient.put(this.url + "/node/id", data, this.httpOptions);
  }
}

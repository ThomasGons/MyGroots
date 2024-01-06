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

  /**
   * Send request to server to get the tree of the account connected, need authentication with a token
   * @param token Authentication token
   * @param accountId Id of the account
   */
  public getFamilyTreeById(token: string, accountId: string): Observable<any> {
    return this._httpClient.post(this.url + "/", {token: token, accountId: accountId}, this.httpOptions);
  }
  
  /**
   * Send request to server to get the tree of the account.
   * @param watcherId Id of the account that wants to watch the family tree
   * @param watchedId Id of the owner of the family tree
   */
  public getOtherFamilyTreeById(watcherId: string, watchedId: string): Observable<any> {
    return this._httpClient.post(this.url + "/view/other", {watcherId: watcherId, watchedId: watchedId}, this.httpOptions);
  }

  /**
   * Send request to server to add a node by names and other informations.
   * @param data Add by names form data
   */
  public addNodeByName(data: any): Observable<any> {
    return this._httpClient.put(this.url + "/node/add/name", data, this.httpOptions);
  }

  /**
   * Send request to server to add a node by an accountID.
   * @param data Add by id form data
   */
  public addNodeById(data: any): Observable<any> {
    return this._httpClient.put(this.url + "/node/add/id", data, this.httpOptions);
  }

  /**
   * Send request to server to delete a node
   * @param data 
   */
  public deleteNode(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/node/delete", data, this.httpOptions);
  }

  /**
   * 
   * @param data 
   */
  public searchInFamilyTree(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/node/search", data, this.httpOptions);
  }
}

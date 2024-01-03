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


  public getPersonByID(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/getPerson", data, this.httpOptions);
  }

  public getFamilyTreeByID(data: any): Observable<any> {
    return this._httpClient.post(this.url + "/", data, this.httpOptions);
  }
}

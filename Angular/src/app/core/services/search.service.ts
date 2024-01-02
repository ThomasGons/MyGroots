import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment.development';


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

  
}

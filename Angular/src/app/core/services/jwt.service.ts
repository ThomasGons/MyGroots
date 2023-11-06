import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class JwtService {

  private readonly keyName: string = "jwtToken";

  public getToken(): string {
    return window.localStorage[this.keyName];
  }

  public saveToken(token: string): void {
    window.localStorage[this.keyName] = token;
  }

  public destroyToken(): void {
    window.localStorage.removeItem(this.keyName);
  }
  
}

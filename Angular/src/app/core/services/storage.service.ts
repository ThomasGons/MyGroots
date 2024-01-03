import { Injectable } from '@angular/core';
import { User } from '../models';


@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private readonly keyName: string = "auth-user";

  /**
   * Save user data in browser storage.
   * @param user 
   */
  public saveUser(user: User): void {
    window.sessionStorage.removeItem(this.keyName);
    window.sessionStorage.setItem(this.keyName, JSON.stringify(user));
  }

  /**
   * Delete user data from browser storage.
   */
  public deleteUser(): void {
    window.sessionStorage.removeItem(this.keyName);
  }

  /**
   * Get user data from browser storage. 
   * @returns user data
   */
  public getUser(): User {
    const user = window.sessionStorage.getItem(this.keyName);
    if (user) {
      return JSON.parse(user);
    } else {
      return {};
    }
  }

  /**
   * Check if user is authenticated.
   * @returns true if authenticated, else false
   */
  public isAuthenticated(): boolean {
    const user = this.getUser();
    if (user && user.token && user.id && user.firstName) {
      return true;
    } else {
      return false;
    }
  }
    
}

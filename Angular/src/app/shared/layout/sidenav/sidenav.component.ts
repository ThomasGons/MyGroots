import { Component, Input, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { StorageService } from '@app/core/services';


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
})
export class SidenavComponent {

  @Input()
  navItems: any;

  @ViewChild('sidenav')
  sidenav!: MatSidenav;

  username: string = "";

  constructor(
    private _storageService: StorageService,
  ) {}

  protected isLoggedIn(): boolean {
    const authenticated: boolean = this._storageService.isAuthenticated();
    if (authenticated) {
      this.username = String(this._storageService.getUser()?.firstName);
      return true;
    }
    return false;
  }

  public toggle(): void {
    this.sidenav.toggle();
  }
  
  protected onActivateRoute(): void {
    this.sidenav.close();
    window.scroll({ 
            top: 0, 
            left: 0, 
            behavior: 'smooth'
     });
  }
  
}

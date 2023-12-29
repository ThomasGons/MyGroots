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

  constructor(
    private _storageService: StorageService,
  ) {}

  public isLoggedIn(): boolean {
    return this._storageService.isAuthenticated();
  }

  public toggle(): void {
    this.sidenav.toggle();
  }
  
  public onActivateRoute(): void {
    this.sidenav.close();
    window.scroll({ 
            top: 0, 
            left: 0, 
            behavior: 'smooth'
     });
  }
  
}

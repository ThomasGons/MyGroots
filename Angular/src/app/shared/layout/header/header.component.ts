import { Component, EventEmitter, Input, Output } from '@angular/core';
import { StorageService } from '@app/core/services';
import { environment } from '@environments/environment.development';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {

  @Input()
  navItems: any;

  @Output()
  signalToggleSidenav = new EventEmitter<void>();

  username: string = "";

  readonly title: string = environment.title;

  constructor(
    private _storageService: StorageService,
  ) {}

  protected onToggleSidenav(): void {
    this.signalToggleSidenav.emit();
  }
  
  protected isLoggedIn(): boolean {
    const authenticated: boolean = this._storageService.isAuthenticated();
    if (authenticated) {
      this.username = String(this._storageService.getUser()?.firstName);
      return true;
    }
    return false;
  }
}

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

  readonly title: string = environment.title;

  constructor(
    private _storageService: StorageService,
  ) {}

  public onToggleSidenav(): void {
    this.signalToggleSidenav.emit();
  }
  
  public isLoggedIn(): boolean {
    return this._storageService.isLoggedIn();
  }
}

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService, SnackbarService } from '@app/core/services';
import { environment } from '@environments/environment.development';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {

  @Input() navItems: any;
  @Output() signalToggleSidenav = new EventEmitter<void>();

  readonly title: string = environment.title;
  isLoggedIn: boolean = false;

  constructor(
    private _storageService: StorageService,
    private _snackbarService: SnackbarService,
    private _router: Router,
  ) {}

  ngOnInit(): void {
    this._storageService.isLoggedIn();
  }

  public onToggleSidenav(): void {
    this.signalToggleSidenav.emit();
  }
  
}

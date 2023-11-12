import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { environment } from '@environments/environment.development';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  title: string = environment.title;
  isLoggedIn: boolean = false;

  @Input() navItems: any;
  @Output() signalToggleSidenav = new EventEmitter<void>();

  public onToggleSidenav(): void {
    this.signalToggleSidenav.emit();
  }
  
  ngOnInit(): void {
  }

}

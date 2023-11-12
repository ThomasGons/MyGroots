import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
})
export class SidenavComponent implements OnInit {

  @Input() navItems: any;
  @ViewChild('sidenav') sidenav!: MatSidenav;

  public toggle(): void {
    this.sidenav.toggle();
  }
  
  ngOnInit(): void {
  }
  
}

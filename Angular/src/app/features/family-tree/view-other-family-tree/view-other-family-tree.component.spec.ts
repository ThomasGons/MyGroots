import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOtherFamilyTreeComponent } from './view-other-family-tree.component';

describe('ViewOtherFamilyTreeComponent', () => {
  let component: ViewOtherFamilyTreeComponent;
  let fixture: ComponentFixture<ViewOtherFamilyTreeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewOtherFamilyTreeComponent]
    });
    fixture = TestBed.createComponent(ViewOtherFamilyTreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

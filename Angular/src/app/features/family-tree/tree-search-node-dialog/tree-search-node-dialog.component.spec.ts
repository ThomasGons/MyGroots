import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TreeSearchNodeDialogComponent } from './tree-search-node-dialog.component';

describe('TreeSearchNodeDialogComponent', () => {
  let component: TreeSearchNodeDialogComponent;
  let fixture: ComponentFixture<TreeSearchNodeDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TreeSearchNodeDialogComponent]
    });
    fixture = TestBed.createComponent(TreeSearchNodeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

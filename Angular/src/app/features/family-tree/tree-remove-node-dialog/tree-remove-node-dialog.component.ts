import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-tree-remove-node-dialog',
  templateUrl: './tree-remove-node-dialog.component.html',
  styleUrls: ["./tree-remove-node-dialog.component.css"]
})
export class TreeRemoveNodeDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<TreeRemoveNodeDialogComponent>,
  ) {}
  
}

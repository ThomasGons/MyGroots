import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-tree-remove-node-dialog',
  templateUrl: './tree-remove-node-dialog.component.html',
})
export class TreeRemoveNodeDialogComponent {
  
  constructor(
    public dialogRef: MatDialogRef<TreeRemoveNodeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {selectedNodeData: any, message: string, buttonText: any}
  ) {}
  
}

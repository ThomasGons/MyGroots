import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSidenav } from '@angular/material/sidenav';
import { Gender, Person, User } from '@app/core/models';
import { StorageService, FamilyTreeService, SnackbarService } from '@app/core/services';
import FamilyTree from "@balkangraph/familytree.js";
import { TreeAddNodeDialogComponent } from './tree-add-node-dialog/tree-add-node-dialog.component';
import { TreeRemoveNodeDialogComponent } from './tree-remove-node-dialog/tree-remove-node-dialog.component';

@Component({
  selector: 'app-family-tree',
  templateUrl: './family-tree.component.html',
  styleUrls: ['./family-tree.component.css'],
})
export class FamilyTreeComponent implements OnInit {

  @ViewChild('sidepanel')
  sidepanel!: MatSidenav;

  formAddByName = new FormGroup({
    relation: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    firstName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    lastName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    birthDate: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    gender: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });
  formAddById = new FormGroup({
    relation: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    accountId: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });
  
  readonly genders: any = [
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ];
  readonly relations: any = [
    {value: "FATHER", viewValue: "Père"},
    {value: "MOTHER", viewValue: "Mère"},
    {value: "PARTNER", viewValue: "Partenaire"},
    {value: "CHILD", viewValue: "Enfant"},
  ];
  
  treeData!: any;
  family!: FamilyTree;
  user!: User;
  selectedNodeId!: number;
  selectedNodePersonData!: any;
  
  showSidePanel: boolean = false;

  constructor(
    private _familytreeService: FamilyTreeService,
    private _storageService : StorageService,
    private _snackbarService: SnackbarService,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.user = this._storageService.getUser();
    /* Send request to get user family tree */
    this._familytreeService.getFamilyTreeById(String(this.user.token), String(this.user.id)).subscribe({
      next: (response) => {
        console.log(response);
        this.treeData = response.body;
        this.initTree(this.treeData);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  // TODO: 
  //    bind events to Add{nodes} buttons => API > Methods
  //    disable addWife when wife pids[0] != -1
  //    common members research
  //    custom research button -> requests (type of relation...) => display result in a div
  //    CSS

  private initTree(treeData: any): void {
    /* Load FamilyTreeJS api */
    const tree = document.getElementById("tree");
    if (tree) {
      /* Config and load tree */
      this.family = new FamilyTree(tree, {
        roots: [0],
        nodeBinding: treeData.nodeBindings,
        nodeMouseClick: FamilyTree.action.none
      });
      this.family.load(treeData.nodes);

      /* Set custom properties */
      FamilyTree.SEARCH_PLACEHOLDER = "Rechercher dans l'arbre";

      /* Set custom events functions */
      this.family.onNodeClick((node: any) => {
        console.log(node);
        this.selectedNodeId = node.node.id;
        this.family.center(this.selectedNodeId);
        if (!this.sidepanel.opened) {
          this.toggleSidePanel();
        }
        this.selectedNodePersonData = this.treeData.members[this.selectedNodeId];
      });
    }
  }
  
  public toggleSidePanel(): void {
    this.sidepanel.toggle();
  }

  public openDialogAddNode(): void {
    const dialogRef = this.dialog.open(TreeAddNodeDialogComponent, {
      data: {
        selectedNodeData: this.selectedNodePersonData,
        nodes: this.treeData.nodes,
      },
      width: "600px",
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      console.log(result);
    });
  }

  public openDialogRemoveNode(): void {
    if (this.selectedNodeId == 0) {
      this._snackbarService.openSnackbar("Vous ne pouvez pas vous supprimer en tant que racine de l'arbre !");
      return;
    }
    const dialogRef = this.dialog.open(TreeRemoveNodeDialogComponent, {
      data: {
        selectedNodeData: this.selectedNodePersonData,
        message: "Etes-vous sur de vouloir supprimer ce noeud de l'arbre ?",
        buttonText: {
          confirm: "Confirmer",
          cancel: "Annuler"
        }
      },
      width: "500px",
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      console.log(confirmed);
    });
  }



  protected getSelectedNodeGender(): string {
    for (let gender of this.genders) {
      if (gender.value == this.selectedNodePersonData?.gender) {
        return gender.viewValue;
      }
    }
    return "";
  }

}

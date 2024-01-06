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
  //    custom research button -> requests (type of relation...) => display result in a div

  private initTree(treeData: any): void {
    /* Load FamilyTreeJS api */
    const tree = document.getElementById("tree");
    if (tree) {
      /* Config and load tree */
      this.family = new FamilyTree(tree, {
        nodeBinding: treeData.nodeBindings,
        nodeMouseClick: FamilyTree.action.none,
      });
      this.family.onInit(() => {
        let root = this.getRootOf(this.family.getNode(this.treeData.nodes.length - 1));
        this.family.config.roots = [root.id];
        this.family.draw();
        this.family.center(0);
      })
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

  public getRootOf(node: any): any {
    while (node) {
      if (!this.family.getNode(node.mid) && !this.family.getNode(node.fid)) {
        break;
      }
      if(this.family.getNode(node.fid)) {
        node = this.family.getNode(node.fid);
      }

      if(this.family.getNode(node.mid)) {
        node = this.family.getNode(node.mid);
      }
    }
    return node;
  }

  protected toggleSidePanel(): void {
    this.sidepanel.toggle();
  }

  protected openDialogAddNode(): void {
    const dialogRef = this.dialog.open(TreeAddNodeDialogComponent, {
      data: {
        ownerId: this.user.id,  // accountID of owner of the tree
        selectedNodeId: this.selectedNodeId,
        selectedNodeData: this.selectedNodePersonData,
        nodes: this.treeData.nodes,
        members: this.treeData.members,
      },
      width: "600px",
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      console.log(result);
      /* Send request to add node by name */
      const type = result?.type;
      if (type == "name") {
        this._familytreeService.addNodeByName(result.formData).subscribe({
          next: (response) => {
            console.log(response);
            this._snackbarService.openSnackbar(response.message);
            this.toggleSidePanel();
            this.ngOnInit();
          },
          error: (err) => {
            console.log(err);
            this._snackbarService.openSnackbar(err.error.message);
            this.toggleSidePanel();
          }
        });
      }
      /* Send request to add node by id */
      if (type == "id") {
        this._familytreeService.addNodeById(result.formData).subscribe({
          next: (response) => {
            console.log(response);
            this._snackbarService.openSnackbar(response.message);
            this.toggleSidePanel();
            this.ngOnInit();
          },
          error: (err) => {
            console.log(err);
            this._snackbarService.openSnackbar(err.error.message);
            this.toggleSidePanel();
          }
        });
      }
    });
  }

  protected openDialogRemoveNode(): void {
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
      if (confirmed) {
        /* Get data */
        const deleteData = {
          token: this.user.token,
          accountId: this.user.id,
          personId: this.treeData.members[this.selectedNodeId].id,
        }
        /* Send request to remove node */
        this._familytreeService.deleteNode(deleteData).subscribe({
          next: (response) => {
            console.log(response);
            this._snackbarService.openSnackbar(response.message);
            this.ngOnInit();
          },
          error: (err) => {
            console.log(err);
            this._snackbarService.openSnackbar(err.error.message);
          }
        });
      }
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

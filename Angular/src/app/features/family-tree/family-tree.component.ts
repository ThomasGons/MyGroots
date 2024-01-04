import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSidenav } from '@angular/material/sidenav';
import { Gender, Person, User } from '@app/core/models';
import { StorageService, FamilyTreeService } from '@app/core/services';
import FamilyTree from "@balkangraph/familytree.js";

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
  selectedNodeData!: any;
  
  showSidePanel: boolean = false;
  showAddForm: boolean = false;
  showRemoveDialog: boolean = false;

  constructor(
    private _familytreeService: FamilyTreeService,
    private _storageService : StorageService,
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
  //    custom panel => remove unwanted buttons
  //    CSS

  public initTree(treeData: any): void {
    /* Load FamilyTreeJS api */
    const tree = document.getElementById("tree");
    if (tree) {
      /* Config and load tree */
      this.family = new FamilyTree(tree, {
        roots: [0],
        nodeBinding: treeData.nodeBindings,
        nodeMouseClick: FamilyTree.action.none,
      });
      this.family.load(treeData.nodes);

      /* Set custom properties */

      FamilyTree.SEARCH_PLACEHOLDER = "Rechercher dans l'arbre";

      /* Set custom events */
      
      this.family.onNodeClick((node: any) => {
        console.log("onNodeCLick");
        console.log(node);
        const nodeData = node.node;
        if (!this.sidepanel.opened) {
          this.toggleSidePanel();
        }
        this.selectedNodeData = this.treeData.members[nodeData.id];
        console.log(this.selectedNodeData);
      });
      
    }
  }
  
  public toggleSidePanel(): void {
    if (this.sidepanel.opened) {
      this.showAddForm = false;
      this.showRemoveDialog = false;
    }
    this.sidepanel.toggle();
  }

  public toggleAddForm(): void {
    this.showAddForm = !this.showAddForm;
  }

  public toggleRemoveDialog(): void {
    this.showRemoveDialog = !this.showRemoveDialog;
  }

  public getGender(gender: string): string {

  }


  public onSubmitAddByName(): void {
    /* Check form */
    /* Send request */
  }

  public onSubmitAddById(): void {

  }

  
  // public onSubmit(): void {
  //   this.form.markAllAsTouched();
  //   if (!this.form.valid) {
  //     return;
  //   }
  // }
  
  // public personOnClick(): void {
  //   this._familytreeServices.getPersonByID(this.Pid).subscribe({
  //     next: (response) => {
  //       console.log(response);
  //       this.nodes = response.body.nodes;
  //       console.log(this.nodes);

  //       const tree = document.getElementById('tree');
  //       if (tree) {
  //         this.family = new FamilyTree(tree, {
  //           nodeBinding: {
  //             field_0: "firstName",
  //             field_1: "lastName",
  //             field_2: "birthDate",
  //             field_4: "nationality",
  //           },
  //           nodeTreeMenu: true,
  //           nodeMenu: {
  //             details: { text: 'Details' },
  //           },
  //           
  //           roots: [0],
  //         });

  //         this.family.load(this.nodes);

  //         this.family.onNodeClick((node) => {
  //           var ID = node.node.id;
  //           console.log("Id de la personne", ID);
  //           this.isselect = true;
  //           // @ts-ignore
  //           this.Pid=ID;
  //         });
  //       }
  //     },
  //     error: (err) => {
  //       console.log(err);
  
  //     }
  //   });
}

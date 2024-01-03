import { Component } from '@angular/core';
import FamilyTree from "@balkangraph/familytree.js";
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { Gender, Person, User } from '@app/core/models';
import { StorageService } from '@app/core/services';
import { FamilyTreeService } from '@app/core/services/family-tree.service';

@Component({
  selector: 'app-family-tree',
  templateUrl: './family-tree.component.html',
  styleUrls: ['./family-tree.component.css'],
})
export class FamilyTreeComponent {

  // name: String | undefined;
  isselect: boolean = false;
  // Pid: string | undefined;
  // person: Person | undefined;
  // user: User | undefined;
  nodes: any [] = [];

  family!: FamilyTree;

  readonly genders: any = [
    { value: Gender.MALE, viewValue: "Homme" },
    { value: Gender.FEMALE, viewValue: "Femme" },
  ];
  readonly links: string[] = [
    "Pere",
    "Mere",
    "Fils",
    "Fille",
    "Grand-pere",
    "Grand-mere",
  ];

  // obj: any [] = [{"id":0,"pids":[-1],"mid":-1,"fid":-1,"firstName":"Matheo","lastName":"Pereira","birthDate":"2002-01-26","gender":"MALE","nationality":"France"}];

  constructor(
    private familytreeServices: FamilyTreeService,
    private storageService : StorageService,
  ) { }

  ngOnInit() {
    // @ts-ignore
    this.user = this.storageService.getUser();
    // @ts-ignore
    this.familytreeServices.getFamilyTreeByID({accountId: this.user.id, token: this.user.token}).subscribe({
      next: (response) => {
        console.log(response);
        this.nodes = response.body.nodes;
        console.log(this.nodes);

        const tree = document.getElementById('tree');
        if (tree) {
          this.family = new FamilyTree(tree, {
            nodeBinding: {
              field_0: "firstName",
              field_1: "lastName",
              field_2: "birthDate",
              field_4: "nationality",
            },
            nodeTreeMenu: true,
            nodeMenu: {
              details: { text: 'Details' },
            },
            // TODO: 
            // bind events to Add{nodes} buttons => API > Methods
            // disable addWife when wife pids[0] != -1
            // common members research
            // custom research button -> requests (type of relation...) => display result in a div
            // custom panel => remove unwanted buttons
            // CSS
            roots: [0],
          });

          this.family.load(this.nodes);

          this.family.onNodeClick((node) => {
            var ID = node.node.id;
            console.log("Id de la personne", ID);
            this.isselect = true;
            // @ts-ignore
            this.Pid=ID;
          });
        }
      },
      error: (err) => {
        console.log(err);

      }
    });
  }

  // form = new FormGroup({
  //   firstName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  //   lastName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  //   birthDate: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  //   gender: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  //   link: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  // });

  

  

  // public onSubmit(): void {
  //   this.form.markAllAsTouched();
  //   if (!this.form.valid) {
  //     return;
  //   }
  // }

  // public personOnClick(): void {
  //   this.familytreeServices.getPersonByID(this.Pid).subscribe({
  //     next: (response) => {
  //       console.log(response);
  //       this.person={firstName: response.firstName,
  //         lastName: response.lastName,
  //         gender: response.gender}
  //     },
  //     error: (err) => {
  //       console.log(err);

  //     }
  //   });
  // }


}

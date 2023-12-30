import { Component } from '@angular/core';
import FamilyTree from "@balkangraph/familytree.js";

@Component({
  selector: 'app-family-tree',
  templateUrl: './family-tree.component.html',
  styleUrls: ['./family-tree.component.css'],
})
export class FamilyTreeComponent {
  constructor() { }

  ngOnInit() {
    const tree = document.getElementById('tree');
    if (tree) {
      var family = new FamilyTree(tree, {
        nodeBinding: {
          field_0: "name",
        },
        nodeTreeMenu: true,
      });

      family.load([
        { id: 1, pids: [2], name: "Amber McKenzie", gender: "female"},
        { id: 2, pids: [1], name: "Ava Field", gender: "male"},
        { id: 3, mid: 1, fid: 2, name: "Peter Stevens", gender: "male"},
        { id: 4, mid: 1, fid: 2, name: "Savin Stevens", gender: "male"},
        { id: 5, mid: 1, fid: 2, name: "Emma Stevens", gender: "female"}
      ]);
    }
  }

}

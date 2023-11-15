package com.springboot.mygroots.dto;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;

import java.util.List;
import java.util.Map;

public class FamilyTreeDTO {
    private Map<String, String> nodeBindings = Map.of(
            "field0", "name"
    );

    private List<TreeNodeFront> nodes;

    public FamilyTreeDTO(FamilyTree tree) {
        this.nodes = new java.util.ArrayList<>();
        List<Person> members = tree.getMembers();
        for (FamilyTree.TreeNode node : tree.getNodes()) {
            int ID = node.getID();
            Person person = members.get(ID);
            nodes.add(new TreeNodeFront(person, node));
        }
    }

    public static class TreeNodeFront {
        private int id;
        private int[] pids;
        private int mid;
        private int fid;
        private String name;
        private String gender;

        public TreeNodeFront(Person person, FamilyTree.TreeNode node) {
            this.id = node.getID();
            this.pids = new int[]{node.getPartnerID()};
            this.mid = node.getMotherID();
            this.fid = node.getFatherID();
            this.name = person.getName();
            this.gender = person.getGender().toString();
        }

        public int getId() {
            return id;
        }

        public int[] getPids() {
            return pids;
        }

        public int getMid() {
            return mid;
        }

        public int getFid() {
            return fid;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }
    }

    public Map<String, String> getNodeBindings() {
        return nodeBindings;
    }

    public List<TreeNodeFront> getNodes() {
        return nodes;
    }
}

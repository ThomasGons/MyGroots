package com.springboot.mygroots.dto;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FamilyTreeDTO {
    private Map<String, String> nodeBindings = Map.of(
            "field0", "firstName",
            "field1", "lastName",
            "field2", "birthDate",
            "field3", "gender",
            "field4", "nationality"
    );

    private List<TreeNodeFront> nodes;
    private List<Person> members;

    public FamilyTreeDTO(FamilyTree tree) {
        this.nodes = new java.util.ArrayList<>();
        List<Person> members = tree.getMembers();
        for (FamilyTree.TreeNode node : tree.getNodes()) {
            int ID = node.getID();
            Person person = members.get(ID);
            nodes.add(new TreeNodeFront(person, node));
        }
        this.members = members;
    }

    public static class TreeNodeFront {
        private int id;
        private int[] pids;
        private int mid;
        private int fid;

        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private String gender;
        private String nationality;



        public TreeNodeFront(Person person, FamilyTree.TreeNode node) {
            this.id = node.getID();
            this.pids = new int[]{node.getPartnerID()};
            this.mid = node.getMotherID();
            this.fid = node.getFatherID();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.birthDate = person.getBirthDate();
            this.gender = person.getGender().toString().toLowerCase();
            this.nationality = person.getNationality();
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

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public String getGender() {
            return gender;
        }

        public String getNationality() {
            return nationality;
        }
    }

    public Map<String, String> getNodeBindings() {
        return nodeBindings;
    }

    public List<TreeNodeFront> getNodes() {
        return nodes;
    }

    public List<Person> getMembers() {
        return members;
    }
}

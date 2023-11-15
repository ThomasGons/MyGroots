package com.springboot.mygroots.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "FamilyTree")
public class FamilyTree {
    @Id
    private String id;
    private String familyName;

    @DBRef
    private List<Person> members;
    private List<TreeNode> nodes;

    public FamilyTree(String familyName, List<Person> members) {
        this.familyName = familyName;
        this.members = members;
        this.nodes = new ArrayList<>();
    }

    public void addNode(Person person, Person partner, Person mother, Person father) {
        int ID = getPersonID(person);
        int partnerID = getPersonID(partner);
        int motherID = getPersonID(mother);
        int fatherID = getPersonID(father);

        TreeNode node = new TreeNode(ID, partnerID, motherID, fatherID);
        nodes.add(node);
    }

    public void removeNode(Person person) {
        int ID = getPersonID(person);
        nodes.removeIf(node -> node.getID() == ID);
    }

    private int getPersonID(Person person) {
        if (person == null) {
            return -1;
        }
        int ID = members.indexOf(person);
        if (ID == -1) {
            addMember(person);
            return members.size() - 1;
        }
        return ID;
    }

    public void addMember(Person person) {
        members.add(person);
    }

    public void removeMember(Person person) {
        members.remove(person);
    }



    public static class TreeNode {
        private int ID;

        private int partnerID;
        private int motherID;
        private int fatherID;

        public TreeNode(int ID, int partnerID, int motherID, int fatherID) {
            this.ID = ID;
            this.partnerID = partnerID;
            this.motherID = motherID;
            this.fatherID = fatherID;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getPartnerID() {
            return partnerID;
        }

        public void setPartnerIDs(int partnerIDs) {
            this.partnerID = partnerIDs;
        }

        public int getMotherID() {
            return motherID;
        }

        public void setMotherID(int motherID) {
            this.motherID = motherID;
        }

        public int getFatherID() {
            return fatherID;
        }

        public void setFatherID(int fatherID) {
            this.fatherID = fatherID;
        }

    }

    public String getFamilyName() {
        return familyName;
    }

    public List<Person> getMembers() {
        return members;
    }

    public List<TreeNode> getNodes() { return nodes; }
}

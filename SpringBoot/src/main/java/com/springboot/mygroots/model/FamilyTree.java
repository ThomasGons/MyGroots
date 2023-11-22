package com.springboot.mygroots.model;

import com.sun.source.tree.Tree;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.springboot.mygroots.utils.Enumerations.*;

import java.beans.Visibility;
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
    @DBRef
    private final Person owner;
    private Visibility visibility;

    public FamilyTree(String familyName, Person owner) {
        this.owner = owner;
        this.familyName = familyName;
        this.members = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.addMember(owner);
        this.addNode(owner, null, null, null);
    }

    private void addNode(Person person, Person partner, Person mother, Person father) {
        if(person == null) {
            System.out.println("person is null");
            return;
        }
        if(this.getNode(person) != null) {
            System.out.println("person node already exists");
            return;
        }
        int ID = getPersonID(person);
        int partnerID = getPersonID(partner);
        int motherID = getPersonID(mother);
        int fatherID = getPersonID(father);

        TreeNode node = new TreeNode(ID, partnerID, motherID, fatherID);
        nodes.add(node);
    }

    public void addPartner(Person member, Person addedMember) {
        int memberID = getPersonID(member);
        if(this.getMembers().contains(member) // member is in the family tree
                && !this.getMembers().contains(addedMember) // addedMember is not in the family tree
                && member.getGender() != addedMember.getGender() // the partner added is not of the same gender
                && nodes.get(memberID).getPartnerID() == -1) // the member does not have a partner
        {
            this.addMember(addedMember);
            int partnerID = getPersonID(addedMember);
            this.getNode(member).setPartnerIDs(partnerID);
            this.addNode(addedMember, member, null, null);
            // update children
            List<TreeNode> childrenNodes = this.getChildrenNodes(member);
            if(childrenNodes != null && !childrenNodes.isEmpty()) {
                childrenNodes.forEach(node -> {
                    if(node.getFatherID() == memberID) {
                        node.setFatherID(partnerID);
                    }
                    else if(node.getMotherID() == memberID) {
                        node.setMotherID(partnerID);
                    }
                });
            }
        }
    }


    public void addFather(Person member, Person addedMember){
        if(addedMember.getBirthDate() !=null && member.getBirthDate()!=null && addedMember.getBirthDate().isBefore(member.getBirthDate())){ // if the father is born before the member
            return;
        }
        int memberID = getPersonID(member);
        if(this.getMembers().contains(member) // member is in the family tree
                && !this.getMembers().contains(addedMember) // addedMember is not in the family tree
                && getNode(member).getFatherID() == -1) // the member does not have a father
        {
            if(this.getNode(member).getMotherID()!= -1){ // if the member has a mother
                this.addPartner(this.getMembers().get(this.getNode(member).getMotherID()), addedMember); // add the father as the partner of the mother
                List<TreeNode> childrenNodes = this.getChildrenNodes(getPartner(addedMember));
                if(childrenNodes != null && !childrenNodes.isEmpty()) {
                    childrenNodes.forEach(node -> {
                        if(node.getFatherID() == memberID) {
                            node.setFatherID(getPersonID(addedMember));
                        }
                    });
                }
            }
            else{
                this.addMember(addedMember);
                int fatherID = getPersonID(addedMember);
                this.getNode(member).setFatherID(fatherID);
                this.addNode(addedMember, null, null, null);
                // update children

            }
        }
    }

    public void addMother(Person member, Person addedMember){
        if(addedMember.getBirthDate() !=null && member.getBirthDate()!=null && addedMember.getBirthDate().isAfter(member.getBirthDate())){ // if the mother is born after the member
            return;
        }
        int memberID = getPersonID(member);
        if(this.getMembers().contains(member) // member is in the family tree
                && !this.getMembers().contains(addedMember) // addedMember is not in the family tree
                && getNode(member).getMotherID() == -1) // the member does not have a mother
        {
            if(this.getNode(member).getFatherID()!= -1){ // if the member has a father
                this.addPartner(this.getMembers().get(this.getNode(member).getFatherID()), addedMember); // add the mother as the partner of the father
                List<TreeNode> childrenNodes = this.getChildrenNodes(getPartner(addedMember)); // update children
                if(childrenNodes != null && !childrenNodes.isEmpty()) {
                    childrenNodes.forEach(node -> {
                        if(node.getMotherID() == memberID) {
                            node.setMotherID(getPersonID(addedMember));
                        }
                    });
                }
            }
            else{
                this.addMember(addedMember);
                int motherID = getPersonID(addedMember);
                this.getNode(member).setMotherID(motherID);
                this.addNode(addedMember, null, null, null);
            }
        }
    }

    public void addChild(Person member, Person addedMember){
        if(addedMember.getBirthDate() !=null && member.getBirthDate()!=null && addedMember.getBirthDate().isAfter(member.getBirthDate())){
            return;
        }
        int memberID = getPersonID(member);
        if(this.getMembers().contains(member) // member is in the family tree
                && !this.getMembers().contains(addedMember)) // addedMember is not in the family tree
        {
            this.addMember(addedMember);
            int childID = getPersonID(addedMember);
            if(member.getGender()== Gender.MALE){
                if(this.getNode(member).getPartnerID() == -1){
                    this.addNode(addedMember, null, null, member);
                }
                else{
                    this.addNode(addedMember, null, this.getMembers().get(this.getNode(member).getPartnerID()), member);
                }

            }
            else{
                if(this.getNode(member).getPartnerID() == -1){
                    this.addNode(addedMember, null, member, null);
                }
                else{
                    this.addNode(addedMember, null, member, this.getMembers().get(this.getNode(member).getPartnerID()));
                }
            }
        }
    }

    public Person getFather(Person member){
        if(this.getMembers().contains(member)){
            if(this.getNode(member).getFatherID() != -1){
                return this.getMembers().get(this.getNode(member).getFatherID());
            }
        }
        return null;
    }

    public Person getMother(Person member){
        if(this.getMembers().contains(member)){
            if(this.getNode(member).getMotherID() != -1){
                return this.getMembers().get(this.getNode(member).getMotherID());
            }
        }
        return null;
    }

    public Person getPartner(Person member){
        if(this.getMembers().contains(member)){
            if(this.getNode(member).getPartnerID() != -1){
                return this.getMembers().get(this.getNode(member).getPartnerID());
            }
        }
        return null;
    }

    public List<Person> getParents(Person member){
        List<Person> parents = new ArrayList<>();
        if(this.getMembers().contains(member)){
            if(this.getNode(member).getFatherID() != -1){
                parents.add(this.getMembers().get(this.getNode(member).getFatherID()));
            }
            if(this.getNode(member).getMotherID() != -1){
                parents.add(this.getMembers().get(this.getNode(member).getMotherID()));
            }
        }
        return parents;
    }

    public List<Person> getChildren(Person member){
        List<Person> children = new ArrayList<>();
        if(this.getMembers().contains(member)){
            List<TreeNode> childrenNodes = this.getChildrenNodes(member);
            if(childrenNodes != null && !childrenNodes.isEmpty()) {
                childrenNodes.forEach(node -> {
                    children.add(this.getMembers().get(node.getID()));
                });
            }
        }
        return children;
    }


    public List<Person> getGrandParents(Person member){
        List<Person> grandParents = new ArrayList<>();
        if(this.getMembers().contains(member)){
            if(this.getNode(member).getFatherID() != -1){
                if(this.getNode(this.getMembers().get(this.getNode(member).getFatherID())).getFatherID() != -1){
                    grandParents.add(this.getMembers().get(this.getNode(this.getMembers().get(this.getNode(member).getFatherID())).getFatherID()));
                }
                if(this.getNode(this.getMembers().get(this.getNode(member).getFatherID())).getMotherID() != -1){
                    grandParents.add(this.getMembers().get(this.getNode(this.getMembers().get(this.getNode(member).getFatherID())).getMotherID()));
                }
            }
            if(this.getNode(member).getMotherID() != -1){
                if(this.getNode(this.getMembers().get(this.getNode(member).getMotherID())).getFatherID() != -1){
                    grandParents.add(this.getMembers().get(this.getNode(this.getMembers().get(this.getNode(member).getMotherID())).getFatherID()));
                }
                if(this.getNode(this.getMembers().get(this.getNode(member).getMotherID())).getMotherID() != -1){
                    grandParents.add(this.getMembers().get(this.getNode(this.getMembers().get(this.getNode(member).getMotherID())).getMotherID()));
                }
            }
        }
        return grandParents;
    }

    public List<Person> getGrandChildren(Person member){
        List<Person> grandChildren = new ArrayList<>();
        if(this.getMembers().contains(member)){
            List<TreeNode> childrenNodes = this.getChildrenNodes(member);
            if(childrenNodes != null && !childrenNodes.isEmpty()) {
                childrenNodes.forEach(node -> {
                    List<TreeNode> grandChildrenNodes = this.getChildrenNodes(this.getMembers().get(node.getID()));
                    if(grandChildrenNodes != null && !grandChildrenNodes.isEmpty()) {
                        grandChildrenNodes.forEach(grandChildNode -> {
                            grandChildren.add(this.getMembers().get(grandChildNode.getID()));
                        });
                    }
                });
            }
        }
        return grandChildren;
    }

    public List<Person> getSiblings(Person member){
        List<Person> siblings = new ArrayList<>();
        for(Person person : this.getParents(member)){
            siblings.addAll(this.getChildren(person));
            siblings.remove(member);
            break;
        }
        return siblings;
    }

    public List<Person> getUnclesAndAunts(Person member){
        List<Person> unclesAndAunts = new ArrayList<>();
        for(Person person : this.getParents(member)){
            unclesAndAunts.addAll(this.getSiblings(person));
        }
        return unclesAndAunts;
    }

    public List<Person> getNephewsAndNieces(Person member){
        List<Person> nephewsAndNieces = new ArrayList<>();
        for(Person person : this.getSiblings(member)){
            nephewsAndNieces.addAll(this.getChildren(person));
        }
        return nephewsAndNieces;
    }

    public List<Person> getCousins(Person member){
        List<Person> cousins = new ArrayList<>();
        for(Person person : this.getUnclesAndAunts(member)){
            cousins.addAll(this.getChildren(person));
        }
        return cousins;
    }





    public Boolean isEquivalant(FamilyTree tree) { // Compare two family trees
        for(Person person : tree.getMembers()) {
            if(this.getMembers().contains(person)) {
                TreeNode node = this.getNode(person);
                TreeNode node2 = tree.getNode(person);
                if((node.getPartnerID() != node2.getPartnerID() && node.getPartnerID() != -1 && node2.getPartnerID() != -1)
                        || (node.getMotherID() != node2.getMotherID() && node.getMotherID() != -1 && node2.getMotherID() != -1)
                        || (node.getFatherID() != node2.getFatherID() && node.getFatherID() != -1 && node2.getFatherID() != -1)){
                    System.out.println("The trees are note equivalant");
                    return false;
                }
            }
        }
        return true;
    }

    public void removeMemberFromTree(Person person) {
        int ID = getPersonID(person);
        if(ID == -1) {
            return;
        }
        // remove the node
        removeNode(person);
        // remove the member
        removeMember(person);
        // remove the children
        nodes.removeIf(node -> node.getFatherID() == ID || node.getMotherID() == ID);
        // remove the partner
        nodes.forEach(node -> {
            if(node.getPartnerID() == ID) {
                node.setPartnerIDs(-1);
            }
        });
    }



    private void removeNode(Person person) {
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

    private void addMember(Person person) {
        members.add(person);
    }

    private void removeMember(Person person) {
        members.remove(person);
    }

    public TreeNode getNode(Person person) {
        int ID = getPersonID(person);
        return nodes.stream().filter(node -> node.getID() == ID).findFirst().orElse(null);
    }

    public List<TreeNode> getChildrenNodes(Person person) {
        int ID = getPersonID(person);
        ArrayList<TreeNode> childrenNodes = new ArrayList<>();
        nodes.forEach(node -> {
            if(node.getFatherID() == ID || node.getMotherID() == ID) {
                childrenNodes.add(node);
            }
        });
        return childrenNodes;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Person getOwner() {
        return owner;
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

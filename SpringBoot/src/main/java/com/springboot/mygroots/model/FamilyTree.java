package com.springboot.mygroots.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.springboot.mygroots.utils.Enumerations.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
    private Visibility visibility = Visibility.PUBLIC;
    private Person unknown = new Person("unknown","unknown",Gender.FEMALE);

    public FamilyTree(String familyName, Person owner) {
        this.owner = owner;
        this.familyName = familyName;
        this.members = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.addMember(owner);
        this.addNode(owner, null, null, null);
        this.visibility = Visibility.PUBLIC;
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

    public void addPersonToTree(Person member, Person addedMember, Relation relation) {
        if(member == null || addedMember == null || relation == null) {
            System.out.println("member or addedMember or relation is null");
            return;
        }

            System.out.println("add member without account");

            boolean verif = false;
            for(Person p: this.getMembers()){
                if(p.getId().equals(member.getId())){
                    verif = true;
                }
                if(p.getId().equals(addedMember.getId())){
                    verif = false;
                }
            }

            if(verif) {

                if (relation == Relation.FATHER) {
                    this.addFather(member, addedMember);
                }
                else if (relation == Relation.MOTHER) {
                    this.addMother(member, addedMember);
                }
                else if (relation == Relation.CHILD) {
                    this.addChild(member, addedMember);
                }
                else if (relation == Relation.PARTNER) {
                    this.addPartner(member, addedMember);
                }
        }

    }

    public void addAccountToTree(Account owner,Person member, Account addedMember, Relation relation) {
        if(member == null || addedMember == null || relation == null || owner == null) {
            System.out.println("member or addedMember or relation is null");
            return;
        }

        System.out.println("add member with account");

        boolean verif = false;
        for(Person p: this.getMembers()){
            if(p.getId().equals(member.getId())){
                verif = true;
            }
            if(p.getId().equals(addedMember.getPerson().getId())){
                verif = false;
            }
        }

        if(verif) {

            if (relation == Relation.FATHER) {
                addedMember.addNotif(new Notif(owner, member, addedMember, NotifType.DEMAND_ADDTOFAMILY, relation));
            }
            else if (relation == Relation.MOTHER) {
                addedMember.addNotif(new Notif(owner, member, addedMember, NotifType.DEMAND_ADDTOFAMILY, relation));
            }
            else if (relation == Relation.CHILD) {
                addedMember.addNotif(new Notif(owner, member, addedMember, NotifType.DEMAND_ADDTOFAMILY, relation));
            }
            else if (relation == Relation.PARTNER) {
                addedMember.addNotif(new Notif(owner, member, addedMember, NotifType.DEMAND_ADDTOFAMILY, relation));
            }

        }

    }

    public void addPartner(Person member, Person addedMember) {
        int memberID = getPersonID(member);
        boolean verif = false;
        for(Person p: this.getMembers()){
            if(p.getId().equals(member.getId())){
                verif = true;
            }
            if(p.getId().equals(addedMember.getId())){
                verif = false;
            }
        }

        if(verif // addedMember is not in the family tree
                && member.getGender() != addedMember.getGender() // the partner added is not of the same gender
                && nodes.get(memberID).getPartnerID() == -1 ) // the member does not have a partner
        {
            System.out.println("add partner");
            this.addMember(addedMember);
            int partnerID = getPersonID(addedMember);
            this.getNode(member).setPartnerIDs(partnerID);
            this.addNode(addedMember, member, null, null);
            // update children
            List<TreeNode> childrenNodes = this.getChildrenNodes(member);
            if(childrenNodes != null && !childrenNodes.isEmpty()) {
                childrenNodes.forEach(node -> {
                    if(node.getMotherID() == memberID && node.getFatherID() != getPersonID(addedMember)) {
                        node.setFatherID(partnerID);
                    }
                    else if(node.getFatherID() == memberID && node.getMotherID() != getPersonID(addedMember)) {
                        node.setMotherID(partnerID);
                    }
                });
            }
            List<TreeNode> childrenNodes2 = this.getChildrenNodes(addedMember);
            if(childrenNodes2 != null && !childrenNodes2.isEmpty()) {
                childrenNodes2.forEach(node -> {
                    if(node.getMotherID() == partnerID && node.getFatherID() != memberID) {
                        node.setFatherID(memberID);
                    }
                    else if(node.getFatherID() == partnerID && node.getMotherID() != memberID) {
                        node.setMotherID(memberID);
                    }
                });
            }
        }
        else if(verif && Objects.equals(members.get(getNode(member).getPartnerID()).getFirstName(), "unknown")){
            members.set(nodes.get(memberID).getPartnerID(), addedMember);
        }
    }


    public void addFather(Person member, Person addedMember){
        int memberID = getPersonID(member);


        if(addedMember.getBirthDate() !=null && member.getBirthDate()!=null && addedMember.getBirthDate().isAfter(member.getBirthDate())){
            System.out.println("father is born before the member");
            return;
        }

        boolean verif = false;
        for(Person p: this.getMembers()){
            if(p.getId().equals(member.getId())){
                verif = true;
            }
            if(p.getId().equals(addedMember.getId())){
                verif = false;
            }
        }

        if(verif // addedMember is not in the family tree
                && nodes.get(memberID).getFatherID() == -1 ) // the member does not have a father
        {
            if(this.getNode(member).getMotherID()!= -1){ // if the member has a mother
                this.addPartner(this.getMembers().get(this.getNode(member).getMotherID()), addedMember); // add the father as the partner of the mother

            }
            else{
                this.addMember(addedMember);
                int fatherID = getPersonID(addedMember);
                this.getNode(member).setFatherID(fatherID);
                this.addNode(addedMember, null, null, null);
            }
        }
        else if(verif && Objects.equals(members.get(getNode(member).getFatherID()).getFirstName(), "unknown")){
            members.set(nodes.get(memberID).getFatherID(), addedMember);
        }

    }

    public void addMother(Person member, Person addedMember){
        if(addedMember.getBirthDate() !=null && member.getBirthDate()!=null && addedMember.getBirthDate().isAfter(member.getBirthDate())){
            return;
        }
        int memberID = getPersonID(member);

        Boolean verif = false;
        for(Person p: this.getMembers()){
            if(p.getId().equals(member.getId())){
                verif = true;
            }
            if(p.getId().equals(addedMember.getId())){
                verif = false;
            }
        }

        if(verif// addedMember is not in the family tree
                && nodes.get(memberID).getMotherID() == -1 ) // the member does not have a mother
        {
            if (this.getNode(member).getFatherID() != -1) { // if the member has a father
                this.addPartner(this.getMembers().get(this.getNode(member).getFatherID()), addedMember); // add the mother as the partner of the father

            } else {
                this.addMember(addedMember);
                int motherID = getPersonID(addedMember);
                this.getNode(member).setMotherID(motherID);
                this.addNode(addedMember, null, null, null);
            }
        }
        else if(verif && Objects.equals(members.get(getNode(member).getMotherID()).getFirstName(), "unknown")){
            members.set(nodes.get(memberID).getMotherID(), addedMember);
        }
    }

    public void addChild(Person member, Person addedMember){
        if(addedMember.getBirthDate() !=null && member.getBirthDate()!=null && addedMember.getBirthDate().isBefore(member.getBirthDate())){
            return;
        }

        Boolean verif = false;
        for(Person p: this.getMembers()){
            if(p.getId().equals(member.getId())){
                verif = true;
            }
            if(p.getId().equals(addedMember.getId())){
                verif = false;
            }
        }


        if(verif) // addedMember is not in the family tree
        {
            this.addMember(addedMember);

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
        if(this.getNode(member).getFatherID() == -1){
            return null;
        }
        return this.getMembers().get(this.getNode(member).getFatherID());
    }

    public Person getMother(Person member){
        if(this.getNode(member).getMotherID() == -1){
            return null;
        }
        return this.getMembers().get(this.getNode(member).getMotherID());
    }

    public Person getPartner(Person member){
        if(this.getNode(member).getPartnerID() == -1){
            return null;
        }
        return this.getMembers().get(this.getNode(member).getPartnerID());
    }

    public List<Person> getParents(Person member){
        List<Person> parents = new ArrayList<>();
        parents.add(this.getFather(member));
        parents.add(this.getMother(member));
        return parents;
    }

    public List<Person> getChildren(Person member){
        List<Person> children = new ArrayList<>();
        List<TreeNode> childrenNodes = this.getChildrenNodes(member);
        if(childrenNodes != null && !childrenNodes.isEmpty()) {
            childrenNodes.forEach(node -> {
                children.add(this.getMembers().get(node.getID()));
            });
        }
        return children;
    }


    public List<Person> getGrandParents(Person member){
        List<Person> grandParents = new ArrayList<>();
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
        return grandParents;
    }

    public List<Person> getGrandChildren(Person member){
        List<Person> grandChildren = new ArrayList<>();
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
        return grandChildren;
    }

    public List<Person> getSiblings(Person member){
        List<Person> siblings = new ArrayList<>();
        for(Person person : this.getParents(member)){
            siblings.addAll(this.getChildren(person));
            for(Person sibling : siblings){
                if(sibling.getId().equals(member.getId())){
                    siblings.remove(sibling);
                }
            }
            break;
        }
        return siblings;
    }

    public List<Person> getUnclesAndAunts(Person member){
        List<Person> unclesAndAunts = new ArrayList<>();
        for(Person person : this.getGrandParents(member)){
            unclesAndAunts.addAll(this.getChildren(person));
        }
        unclesAndAunts.removeAll(this.getParents(member));
        //remove duplicates
        unclesAndAunts = new ArrayList<>(new HashSet<>(unclesAndAunts));
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

        for(Person person : this.getMembers()){
            if(tree.getMembers().contains(person)){
                if(getPartner(person) != null && tree.getPartner(person) != null){
                    if(!getPartner(person).equals(tree.getPartner(person))){
                        return false;
                    }
                }
                if(getFather(person) != null && tree.getFather(person) != null){
                    if(!getFather(person).equals(tree.getFather(person))){
                        return false;
                    }
                }
                if(getMother(person) != null && tree.getMother(person) != null){
                    if(!getMother(person).equals(tree.getMother(person))){
                        return false;
                    }
                }
                List<Person> children = getChildren(person);
                List<Person> children2 = tree.getChildren(person);
                if(children != null && children2 != null){
                    if(children.size() == children2.size()){
                        if(!new HashSet<>(children).containsAll(children2)){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    //remove a member from the family tree and replace it by temporary member "unknown"
    public void removeMemberFromTree(Person person) {
        TreeNode node = this.getNode(person);
        System.out.println(node.getID());

        if ( getChildren(members.get(getPersonID(person))).isEmpty()  && node.getPartnerID() == -1) {
            System.out.println("remove");
            int removedID = getPersonID(person);
            nodes.remove(node);
            members.remove(getPersonID(person));

            for (TreeNode extNode : this.getNodes()) {
                if (extNode.getID() > removedID) {
                    extNode.setID(extNode.ID - 1);
                }
                if (extNode.getFatherID() > removedID) {
                    extNode.setFatherID(extNode.fatherID - 1);
                }
                if (extNode.getMotherID() > removedID) {
                    extNode.setMotherID(extNode.motherID - 1);
                }
                if (extNode.getPartnerID() > removedID) {
                    extNode.setPartnerIDs(extNode.partnerID - 1);
                }
            }

        }
        else{
            members.set(getPersonID(person), unknown);
            System.out.println("unknown");
        }


    }

    private void removeNode(Person person) {
        int ID = getPersonID(person);
        nodes.removeIf(node -> node.getID() == ID);
    }

    private int getPersonID(Person person) {
        if (person == null) {
            return -1;
        }
        int ID = findIndexInMemberList(person);
        if (ID == -1) {
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
        int personId = getPersonID(person);
        for(TreeNode node : nodes) {
            if(Integer.valueOf(node.getID()).equals(personId)) {

                return node;
            }
        }
        return null;
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

    public int findIndexInMemberList(Person person) {
        for(int i = 0; i < members.size(); i++) {
            if(members.get(i).getId().equals(person.getId())) {
                return i;
            }
        }
        return -1;
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

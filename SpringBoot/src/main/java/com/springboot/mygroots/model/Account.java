package com.springboot.mygroots.model;

import com.mongodb.lang.Nullable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Account")
public class Account  {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @DBRef
    private Person person;

    @DBRef
    private FamilyTree familyTree;

    @DBRef
    private List<Notif> notifs = new ArrayList<>();

    private boolean isActive;

    public Account(String email, @Nullable Person person) {
        this.email = email;
        this.person = person;
        this.isActive = false;
    }



    public void activate() {
        this.isActive = true;
    }


    public List<Notif> getNotifs() {
        return notifs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FamilyTree getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(FamilyTree familyTree) {
        this.familyTree = familyTree;
    }

    public Person getPerson() {
        return person;
    }

    public void setPersonID(Person person) {
        this.person = person;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getId() {
        return id;
    }
}

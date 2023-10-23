package com.springboot.mygroots.model;

import com.mongodb.lang.Nullable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Account")
public class Account  {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @DBRef
    private Person person;

    private boolean isActive;

    public Account(String email, @Nullable Person person) {
        this.email = email;
        this.person = person;
        this.isActive = false;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void activate() {
        this.isActive = true;
    }

    public String getId() {
        return id;
    }
}

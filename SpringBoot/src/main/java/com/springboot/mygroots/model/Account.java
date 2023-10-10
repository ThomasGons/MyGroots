package com.springboot.mygroots.model;

import com.mongodb.lang.Nullable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Accounts")
public class Account  {
    @Id
    private String id;

    private String email;

    @DBRef
    private Person person;

    public Account(String email, @Nullable Person person) {
        this.email = email;
        this.person = person;
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

}

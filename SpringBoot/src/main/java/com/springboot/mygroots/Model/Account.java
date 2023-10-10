package com.springboot.mygroots.Model;

import com.mongodb.lang.Nullable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;

@Document(collection = "Accounts")
public class Account  {
    @Id@Generated("auto")
    private int id;

    private String email;


    private Person person;

    public Account(String email, Person person) {
        this.email = email;
        this.person = person;
    }


    public String getEmail() {
        return email;
    }

    public Person getPerson() {
        return person;
    }

    public int getId() {
        return id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

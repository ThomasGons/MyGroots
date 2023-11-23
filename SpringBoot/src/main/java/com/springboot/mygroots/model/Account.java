package com.springboot.mygroots.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Account")
public class Account implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Id
    private String id;

    @Indexed(unique = true)
    private String email;
    
    private String password;
    
    private boolean verified;
    
    @DBRef
    private Person person;

    public Account(String email, String password, boolean verified, Person person) {
		this.email = email;
		this.password = password;
		this.verified = verified;
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

    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public boolean getVerified() {
    	return verified;
    }
    
    public void setVerified(boolean verified) {
    	this.verified = verified;
    }
    
}

package com.springboot.mygroots.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;
import com.springboot.mygroots.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Account")
public class Account implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Id
    private String id;

    @Indexed(unique = true)
    private String email;
    
    private String password;
    
    private String token;
    
    @DBRef
    private Person person;
    
    private boolean isActive;

    @DBRef
    private FamilyTree familyTree;

    @DBRef
    private List<Notif> notifs = new ArrayList<>();

    @DBRef
    private FamilyTree myFamilyTree;

    public Account(String email, String password, @Nullable Person person, @Nullable String token) {
        this.email = email;
        this.password = password;
        this.person = person;
        this.isActive = false;
        this.token = token;
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

    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = Utils.encode(password);
    }
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
	public boolean isAuthenticated(String token) {
		return this.getToken().equals(token);
	}
	
	public String generateToken() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		String token = Utils.encode(currentDateTime.toString());
		this.setToken(token);
		return token;
	}
	
	public void resetToken() {
		this.setToken("");
	}
}

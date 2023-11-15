package com.springboot.mygroots.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Email")
public class Email {
    @Id
    private String id;
    @DBRef
    private Person source;
    @DBRef
    private Person target;
    private FamilyGraph.FamilyRelation relation;
    private String body;



    public Email(Person source, Person target, FamilyGraph.FamilyRelation relation, String body) {
        this.source = source;
        this.target = target;
        this.relation = relation;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person getSource() {
        return source;
    }

    public void setSource(Person source) {
        this.source = source;
    }

    public Person getTarget() {
        return target;
    }

    public void setTarget(Person target) {
        this.target = target;
    }
    public FamilyGraph.FamilyRelation getRelation() {
        return relation;
    }

    public void setRelation(FamilyGraph.FamilyRelation relation) {
        this.relation = relation;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}

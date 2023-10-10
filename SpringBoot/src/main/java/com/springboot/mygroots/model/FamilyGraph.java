package com.springboot.mygroots.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "FamilyGraph")
public class FamilyGraph {

    @Id
    private String id;
    private List<FamilyRelationEdge> relationShips;

    public FamilyGraph() {
        this.relationShips = new ArrayList<>();
    }

    public void addRelation(Person source, Person target, FamilyRelation relation) {
        this.relationShips.add(new FamilyRelationEdge(source, target, relation));
    }

    public static class FamilyRelationEdge {

        @DBRef
        private Person source;
        @DBRef
        private Person target;
        private FamilyRelation relation;

        public FamilyRelationEdge(Person source, Person target, FamilyRelation relation) {
            this.source = source;
            this.target = target;
            this.relation = relation;
        }

    }

    public enum FamilyRelation {
        FATHER,
        MOTHER,
        SON,
        DAUGHTER,
        BROTHER,
        SISTER,
        HUSBAND,
        WIFE,
        GRANDFATHER,
        GRANDMOTHER,
        GRANDSON,
        GRANDDAUGHTER,
        UNCLE,
        AUNT,
        NEPHEW,
        NIECE,
        COUSIN,
        NO_RELATION
    }
}

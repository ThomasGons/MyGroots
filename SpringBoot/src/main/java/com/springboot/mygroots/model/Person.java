package com.springboot.mygroots.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Person")
public class Person {
    @Id
    private String id;
    private String name;
    private String lastName;
    private LocalDate birthDate=null;
    private LocalDate deathDate;
    private String birthPlace;
    private String deathPlace;
    private Gender gender;
    private Status status;

    public Person(String name, String lastName, Gender gender){
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum Status {
        ALIVE,
        DEAD
    }



}




package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.FamilyGraph;
import com.springboot.mygroots.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FamilyGraphRepository extends MongoRepository<FamilyGraph, String> {
    FamilyGraph getFamilyGraphByFamilyName(String familyName);
    FamilyGraph getFamilyGraphByOwnersContaining(Person owner);
}

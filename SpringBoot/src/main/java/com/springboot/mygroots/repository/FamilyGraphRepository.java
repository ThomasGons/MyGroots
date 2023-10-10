package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.FamilyGraph;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FamilyGraphRepository extends MongoRepository<FamilyGraph, String> {

}

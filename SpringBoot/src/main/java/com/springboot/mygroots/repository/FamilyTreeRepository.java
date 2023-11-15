package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.FamilyTree;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FamilyTreeRepository extends MongoRepository<FamilyTree, String>{
}

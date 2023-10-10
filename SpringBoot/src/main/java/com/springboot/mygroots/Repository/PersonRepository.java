package com.springboot.mygroots.Repository;

import com.springboot.mygroots.Model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Integer> {
}

package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.Person;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}

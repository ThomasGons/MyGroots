package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.Email;
import com.springboot.mygroots.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmailRepository extends MongoRepository<Email, String> {
    List<Email> getMailByTarget(Person target);
}

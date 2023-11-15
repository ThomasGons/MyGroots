package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account getAccountByEmail(String email);
    Account getAccountById(String id);
    FamilyTree getFamilyTreeByPerson(Person person);
}

package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account getAccountByEmail(String email);
    
}

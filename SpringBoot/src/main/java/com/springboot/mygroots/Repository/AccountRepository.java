package com.springboot.mygroots.Repository;

import com.springboot.mygroots.Model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, Integer> {
}

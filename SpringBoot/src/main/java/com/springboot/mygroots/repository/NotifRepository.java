package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.Notif;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotifRepository extends MongoRepository<Notif, String> {
}

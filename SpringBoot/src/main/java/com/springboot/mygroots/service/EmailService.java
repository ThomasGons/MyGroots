package com.springboot.mygroots.service;

import com.springboot.mygroots.model.Email;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    void sendMail(Email email) {
        emailRepository.save(email);
    }

    void deleteMail(Email email) {
        emailRepository.delete(email);
    }

    public List<Email> getInbox(Person person) {
        return emailRepository.getMailByTarget( person);
    }

}

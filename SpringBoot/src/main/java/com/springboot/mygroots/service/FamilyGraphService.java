package com.springboot.mygroots.service;

import com.springboot.mygroots.model.Email;
import com.springboot.mygroots.model.FamilyGraph;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.EmailRepository;
import com.springboot.mygroots.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.mygroots.repository.FamilyGraphRepository;

import java.util.List;

@Service
public class FamilyGraphService {
    @Autowired
    private FamilyGraphRepository familyGraphRepository;

    @Autowired
    private EmailRepository emailRepository;

    public void addFamilyGraph(FamilyGraph familyGraph){
        familyGraphRepository.save(familyGraph);
    }

    public List<FamilyGraph> getAllFamilyGraphs(){
        return familyGraphRepository.findAll();
    }

    public FamilyGraph getByFamilyName(String familyName){
        return familyGraphRepository.getFamilyGraphByFamilyName(familyName);
    }

    public void addRelation(FamilyGraph familyGraph, Person source, Person target, FamilyGraph.FamilyRelation relation) {
        familyGraph.addRelation(source, target, relation);
        familyGraphRepository.save(familyGraph);
        Email email = new Email(source, target, relation, "ADD");
        emailRepository.save(email);
    }

    public void addOwner(FamilyGraph familyGraph, Person owner){
        familyGraph.addOwner(owner);
        familyGraphRepository.save(familyGraph);
    }

    public FamilyGraph getFamilyGraphByOwner(Person owner){
        return familyGraphRepository.getFamilyGraphByOwnersContaining(owner);
    }

    public void updateFamilyGraph(FamilyGraph familyGraph) {
        familyGraphRepository.save(familyGraph);
    }
}

package com.springboot.mygroots.service;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.FamilyTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FamilyTreeService {
    @Autowired
    private FamilyTreeRepository familyTreeRepository;

    @Autowired
    private PersonService personService;

    public void saveFamilyTree(FamilyTree familyTree) {

        familyTreeRepository.save(familyTree);

    }

    public void deleteFamilyTree(FamilyTree familyTree) {
        familyTreeRepository.delete(familyTree);
    }

    public FamilyTree getFamilyTreeById(String id) {
        return familyTreeRepository.findById(id).get();
    }
    
    public FamilyTree getFamilyTreeByOwner(Person owner) {
    	return familyTreeRepository.findFamilyTreeByOwner(owner);
    }

    public void updateFamilyTree(FamilyTree familyTree) {
        for(Person person : familyTree.getMembers()){
            personService.updatePerson(person);
        }
        familyTreeRepository.save(familyTree);
    }

    public List<FamilyTree> getAllFamilyTrees() {
        return familyTreeRepository.findAll();
    }

    public void removeFamilyTree(FamilyTree familyTree) {
        familyTreeRepository.delete(familyTree);
    }

    public List<String> getHelp() {
    	return List.of (
                "father", "mother", "partner", "parents",
                "children", "siblings", "grandparents",
                "grandchildren", "cousins", "uncles_aunts",
                "nephews_nieces"
        );
    }
}

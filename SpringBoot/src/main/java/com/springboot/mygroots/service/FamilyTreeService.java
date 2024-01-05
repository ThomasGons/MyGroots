package com.springboot.mygroots.service;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.FamilyTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    //TODO: préciser si c'est ID de person ou de account
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

    public Map<String, List<Person>> getSimilarFamilyTreeByAllNodes(Person owner) {
        FamilyTree ft = getFamilyTreeByOwner(owner);

        List<FamilyTree> familyTrees = familyTreeRepository.findAll();
        Set<Person> same = new HashSet<>();
        Set<Person> probably_same = new HashSet<>();
        for (Person p: ft.getMembers()) {
            getSimilarFamilyTreeByNode(p, owner, familyTrees, same, probably_same);
        }
        return Map.of(
                "same", same.stream().toList(),
                "probably_same", probably_same.stream().toList()
        );
    }

    private void getSimilarFamilyTreeByNode(Person p, Person owner, List<FamilyTree> familyTrees, Set<Person> same, Set<Person> probably_same) {
        String p_id = p.getId();
        String p_firstName = p.getFirstName();
        String p_lastName = p.getLastName();
        LocalDate p_birthDate = p.getBirthDate();
        for (FamilyTree ft: familyTrees) {
            if (ft.getOwner().getId().equals(owner.getId())) {
                continue;
            }
            for (Person p2: ft.getMembers()) {
                if (p2.getId().equals(p.getId())) {
                    same.add(ft.getOwner());
                    return;
                }
                else if (p2.getFirstName().equals(p_firstName) &&
                        p2.getLastName().equals(p_lastName) &&
                        p2.getBirthDate().equals(p_birthDate) &&
                        !p2.hasAccount()) {

                    probably_same.add(ft.getOwner());
                    return;
                }
            }
        }
    }


}

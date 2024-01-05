package com.springboot.mygroots.service;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.FamilyTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        List<Person> same = new ArrayList<>();
        List<Person> probably_same = new ArrayList<>();
        for (Person p: ft.getMembers()) {
            getSimilarFamilyTreeByNode(p, familyTrees, same, probably_same);
        }
        return Map.of(
                "same", same,
                "probably_same", probably_same
        );
    }

    private void getSimilarFamilyTreeByNode(Person p, List<FamilyTree> familyTrees, List<Person> same, List<Person> probably_same) {
        for (FamilyTree ft: familyTrees) {
            if (ft.getOwner().getId().equals(p.getId())) {
                continue;
            }
            for (Person p2: ft.getMembers()) {
                if (p2.getId().equals(p.getId())) {
                	same.add(p2);
                	break;
                }
                if (p2.getFirstName().equals(p.getFirstName()) || p2.getLastName().equals(p.getLastName())) {
                    probably_same.add(p2);
                    break;
                }
            }
        }
    }
}

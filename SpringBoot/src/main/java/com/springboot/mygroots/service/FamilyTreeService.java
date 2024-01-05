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

    public Map<String, List<Person>> getSimilarNodes(Person src, String target_id) {
        Person target = personService.getPersonById(target_id);
        FamilyTree ft_src = getFamilyTreeByOwner(src);
        FamilyTree ft_target = getFamilyTreeByOwner(target);

        Set<Person> same = new HashSet<>();
        Set<Person> probably_same = new HashSet<>();

        List<Person> src_members = ft_src.getMembers();
        List<Person> target_members = ft_target.getMembers();

        // get all the members that have the same id in the two family trees
        for (Person p: src_members) {
            if (Objects.equals(p.getId(), src.getId())) {continue;}
            for (Person p2: target_members) {
                if (Objects.equals(p2.getId(), target_id)) {break;}
                    if (p.getId().equals(p2.getId())) {
                        same.add(p);
                    } else if (p.getFirstName().equals(p2.getFirstName()) &&
                            p.getLastName().equals(p2.getLastName()) &&
                            p.getBirthDate().equals(p2.getBirthDate()) &&
                            !p.hasAccount() || !p2.hasAccount()) {
                        probably_same.add(p);
                    }
                }
        }

        return Map.of(
                "same", same.stream().toList(),
                "probably_same", probably_same.stream().toList()
        );
    }

}

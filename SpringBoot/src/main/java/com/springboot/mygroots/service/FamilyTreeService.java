package com.springboot.mygroots.service;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.repository.FamilyTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyTreeService {
    @Autowired
    private FamilyTreeRepository familyTreeRepository;

    public void saveFamilyTree(FamilyTree familyTree) {
        familyTreeRepository.save(familyTree);
    }

    public void deleteFamilyTree(FamilyTree familyTree) {
        familyTreeRepository.delete(familyTree);
    }

    public FamilyTree getFamilyTreeById(String id) {
        return familyTreeRepository.findById(id).get();
    }

    public void updateFamilyTree(FamilyTree familyTree) {
        familyTreeRepository.save(familyTree);
    }

    public List<FamilyTree> getAllFamilyTrees() {
        return familyTreeRepository.findAll();
    }

    public void removeFamilyTree(FamilyTree familyTree) {
        familyTreeRepository.delete(familyTree);
    }
}

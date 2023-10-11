package com.springboot.mygroots.service;

import com.springboot.mygroots.model.FamilyGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.mygroots.repository.FamilyGraphRepository;

import java.util.List;

@Service
public class FamilyGraphService {
    @Autowired
    private FamilyGraphRepository familyGraphRepository;

    public void addFamilyGraph(FamilyGraph familyGraph){
        familyGraphRepository.save(familyGraph);
    }

    public List<FamilyGraph> getAllFamilyGraphs(){
        return familyGraphRepository.findAll();
    }

    public FamilyGraph getByFamilyName(String familyName){
        return familyGraphRepository.getFamilyGraphByFamilyName(familyName);
    }
}

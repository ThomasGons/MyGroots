package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.mygroots.dto.FamilyTreeDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/family-tree")
@CrossOrigin(origins = "http://localhost:4200")
public class FamilyTreeCtrl {

    @Autowired
    FamilyTreeService familyTreeService;

    @Autowired
    PersonService personService;
    
    @Autowired
    AccountService accountService;

    //TODO : verifier que c'est bien id du compte et pas de la personne
    @GetMapping(value= "/")
    public FamilyTreeDTO root(@RequestBody Map<String, String> data) {
    	Account acc = accountService.AuthentificatedUser(data.get("token"), data.get("id"));
		if ( acc != null) {
	        FamilyTree ft = familyTreeService.getFamilyTreeById(data.get("id"));
	        return new FamilyTreeDTO(ft);
		}
		return null;
    }

    @GetMapping(value= "/help")
    public List<String> help() {
        return familyTreeService.getHelp();
    }

    @GetMapping(value="/nodes")
    public List<Person> search(@RequestBody String src_id, @RequestBody String relation, @RequestBody String owner_id) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(owner_id == null ? src_id : owner_id);
        Person p = personService.getPersonById(src_id);
        return switch (relation) {
            case "father" -> List.of(ft.getFather(p));
            case "mother" -> List.of(ft.getMother(p));
            case "partner" -> List.of(ft.getPartner(p));
            case "parents" -> ft.getParents(p);
            case "children" -> ft.getChildren(p);
            case "siblings" -> ft.getSiblings(p);
            case "grandparents" -> ft.getGrandParents(p);
            case "grandchildren" -> ft.getGrandChildren(p);
            case "cousins" -> ft.getCousins(p);
            case "uncles_aunts" -> ft.getUnclesAndAunts(p);
            case "nephews_nieces" -> ft.getNephewsAndNieces(p);
            default -> null;
        };
    }


    @PutMapping(value="/nodes")
    public void addNode(@RequestBody String src_id, @RequestBody String dst_id, @RequestBody String relation, @RequestBody String owner_id) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(owner_id == null ? src_id : owner_id);
        Person sp = personService.getPersonById(src_id);
        Person dp = personService.getPersonById(dst_id);
        ft.addMemberToTree(sp, dp, Enumerations.Relation.valueOf(relation));
        familyTreeService.updateFamilyTree(ft);
    }

    @DeleteMapping(value="/nodes")
    public void removeNode(@RequestBody String owner_id, @RequestBody String removal_id) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(owner_id);
        Person p = personService.getPersonById(removal_id);
        ft.removeMemberFromTree(p);
        familyTreeService.updateFamilyTree(ft);
    }

}
